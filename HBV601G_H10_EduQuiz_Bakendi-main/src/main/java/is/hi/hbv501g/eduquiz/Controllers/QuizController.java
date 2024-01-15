package is.hi.hbv501g.eduquiz.Controllers;

import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.DTO.QuizOptionsDto;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.Entities.Type;
import is.hi.hbv501g.eduquiz.Entities.User;
import is.hi.hbv501g.eduquiz.Services.QuizService;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class QuizController {

    private QuizService quizService;
    private UserService userService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService) {
        this.userService = userService;
        this.quizService = quizService;
    }

    @RequestMapping(value="/addquiz", method = RequestMethod.POST)
    public String addNewQuiz(@Valid QuizOptionsDto quizOptionsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-quiz";
        }

        String path = "redirect:/addquiz/questions=" + quizOptionsDto.getNumberOfQuestions() + "&answers="
                + quizOptionsDto.getNumberOfAnswers() +"&type=" +quizOptionsDto.getType().ordinal();

        return path;
    }

    @RequestMapping(value="/addquiz", method = RequestMethod.GET)
    public String getQuizOptionsForm(QuizOptionsDto quizOptionsDto, Model model) {
        //Val á default hakað við
        quizOptionsDto.setType(Type.questionsAreText);
        return "add-quiz";
    }

    @RequestMapping(value="/postquiz", method = RequestMethod.POST, params={"addquestion"})
    public String addQuestionAndAnswers(@ModelAttribute QuizFormDto quizFormDto,
                                        BindingResult bindingResult, Model model, HttpSession session) {
        quizService.setQuizFormDtoType((int)session.getAttribute("typeEnumOrdinal"), quizFormDto);

        //Bæta við einni spurningu, og tilheyrandi svörum
        int numberOfAnswers = quizFormDto.getListOfAnswers().get(0).size();
        quizService.addQuestionsAndAnswers(quizFormDto, 1, numberOfAnswers);

        return "quiz-form";

    }

    @RequestMapping(value="/postquiz", method = RequestMethod.POST, params={"confirm"})
    public String saveQuiz(@ModelAttribute @Valid QuizFormDto quizFormDto, BindingResult bindingResult, Model model, HttpSession session) {

        quizService.setQuizFormDtoType((int)session.getAttribute("typeEnumOrdinal"), quizFormDto);

        if (bindingResult.hasErrors()) {
            return "quiz-form";
        }


        Quiz quiz = quizService.mapToQuiz(quizFormDto);

        String currentUserName = ((User)session.getAttribute("LoggedInUser")).getUserName();
        User currentUser = userService.findByUserName(currentUserName);

        userService.save(currentUser);
        userService.addQuizToUser(currentUser, quiz);
        quizService.setCreator(quiz, currentUser);
        quizService.save(quiz);

        return "redirect:/";
    }

    @RequestMapping(value="/addquiz/questions={q}&answers={a}&type={t}", method = RequestMethod.GET)
    public String getQuizForm(@ModelAttribute("quizFormDto") QuizFormDto quizFormDto,
                              @PathVariable("q") int numberOfQuestions,
                              @PathVariable("a") int numberOfAnswers,
                              @PathVariable("t") int typeEnumOrdinal, Model model,
                              HttpSession session) {
        int MIN_QUESTIONS = 1;
        int MAX_QUESTIONS = 50;
        int MIN_ANSWERS = 2;
        int MAX_ANSWERS = 6;
        int MAX_TYPE_ORDINAL = Type.values().length -1;

        if (numberOfQuestions < MIN_QUESTIONS || numberOfQuestions > MAX_QUESTIONS
        || numberOfAnswers < MIN_ANSWERS || numberOfAnswers > MAX_ANSWERS
        || typeEnumOrdinal < 0 || typeEnumOrdinal > MAX_TYPE_ORDINAL ) {

            String path = "redirect:/addquiz";

            return path;
        }

        quizService.addQuestionsAndAnswers(quizFormDto, numberOfQuestions, numberOfAnswers);
        quizService.setQuizFormDtoType(typeEnumOrdinal, quizFormDto);

        session.setAttribute("typeEnumOrdinal", typeEnumOrdinal);

        return "quiz-form";
    }

    @RequestMapping(value="/editquiz/{id}", method = RequestMethod.POST, params={"confirm"})
    public String addUpdatedQuizRemoveOld(@ModelAttribute @Valid QuizFormDto quizFormDto,
                                          BindingResult bindingResult, @PathVariable("id") long id, Model model,
                                          HttpSession session) {
        quizService.setQuizFormDtoType((int)session.getAttribute("typeEnumOrdinal"), quizFormDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("oldQuizId", id);
            return "edit-quiz";
        }

        Quiz oldQuiz = quizService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz ID"));

        Quiz quiz = quizService.mapToQuiz(quizFormDto);

        //Upplýsingar sem er ekki partur af formi þarf að copya
        quizService.setInfoFromOldQuiz(oldQuiz, quiz);

        quizService.delete(oldQuiz);

        String currentUserName = ((User)session.getAttribute("LoggedInUser")).getUserName();
        User currentUser = userService.findByUserName(currentUserName);
        userService.save(currentUser);
        userService.addQuizToUser(currentUser, quiz);
        quizService.setCreator(quiz, currentUser);

        quizService.save(quiz);

        return "redirect:/";
    }

    @RequestMapping(value= {"/editquiz/{id}"}, method = RequestMethod.GET)
    public String editQuizForm(@PathVariable("id") long id, Model model,
                               @RequestParam(value="addingquestion", required = false) Boolean addingquestion,
                               HttpSession session) {

        Quiz quiz = quizService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));

        QuizFormDto quizFormDto = quizService.mapToQuizFormDto(quiz);
        model.addAttribute("quizFormDto", quizFormDto);

        model.addAttribute("oldQuizId", id);

        session.setAttribute("typeEnumOrdinal", quizFormDto.getType().ordinal());

        return "edit-quiz";
    }

    @RequestMapping(value="/editquiz/{id}", method = RequestMethod.POST, params={"addquestion"})
    public String addQuestionAndAnswers(@ModelAttribute QuizFormDto quizFormDto,
                                        BindingResult bindingResult, @PathVariable("id") long id, Model model,
                                        HttpSession session) {

        quizService.setQuizFormDtoType((int)session.getAttribute("typeEnumOrdinal"), quizFormDto);

        int numberOfAnswers = quizFormDto.getListOfAnswers().get(0).size();
        quizService.addQuestionsAndAnswers(quizFormDto, 1, numberOfAnswers);

        model.addAttribute("oldQuizId", id);

        return "edit-quiz";

    }

    @RequestMapping(value = "/play/{id}")
    public String playQuiz(@PathVariable("id") long id, Model model) {
        return "play-quiz";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteQuiz(@PathVariable("id") long id, Model model) {
        Quiz quiz = quizService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz ID"));
        quizService.delete(quiz);
        return "redirect:/";
    }
}
