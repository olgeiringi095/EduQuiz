package is.hi.hbv501g.eduquiz.Controllers;

import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.DTO.Response;
import is.hi.hbv501g.eduquiz.Entities.User;
import is.hi.hbv501g.eduquiz.Services.QuizService;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/play")
public class PlayQuizRestController {
    private QuizService quizService;
    private UserService userService;

    @Autowired
    public PlayQuizRestController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public Response getResource() {
        /*Quiz quiz = quizService.findById(3).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz ID"));*/
        List<Quiz> list = quizService.findAll();

        Response response = new Response("Done", list);

        return response;
    }
    @GetMapping(value = "/{id}/api/quiz")
    public Response getById(@PathVariable("id") long id, Model model) {

        Quiz quiz = quizService.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Invalid quiz id"));

        quizService.randomizeAnswers(quiz);

        Response response = new Response("Done", quiz);
        return response;
    }
    @PostMapping(value = "/user/add")
    User nyr(@RequestBody User nyr) {

        return userService.save(nyr);
    }
    @PostMapping(value = "/resent")
    ResponseEntity<Quiz> test(@RequestBody QuizFormDto nytt) {
        Quiz qu = quizService.mapToQuiz(nytt);
        qu.setCreator(userService.findUserNameById(2));
        return new ResponseEntity<Quiz>(qu, HttpStatus.OK);
    }
    @PostMapping(value = "/quiz/add")
    Optional<Quiz> nyttQuiz(@RequestBody QuizFormDto nyttQuiz) {
        User not = userService.findByUserName("svag");
        Quiz qu = quizService.mapToQuiz(nyttQuiz);
        qu.setCreator(userService.findUserNameById(2));
        quizService.save(qu);
        return quizService.findById(7);
    }
    @PostMapping(value = "/{id}/finished/{correctAnswers}")
    public void postScoreAndIncPlays(@PathVariable("id") long id, @PathVariable("correctAnswers") int correctAnswers) {
        Quiz quiz = quizService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz id"));

        quizService.incrementTotalPlays(quiz);
        quizService.calcAverageScore(quiz, correctAnswers);
        quizService.save(quiz);
    }
}
