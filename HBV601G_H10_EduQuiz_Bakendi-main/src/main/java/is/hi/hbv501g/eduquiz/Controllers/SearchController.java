package is.hi.hbv501g.eduquiz.Controllers;

import is.hi.hbv501g.eduquiz.DTO.QuizOptionsDto;
import is.hi.hbv501g.eduquiz.DTO.QuizSearchDto;
import is.hi.hbv501g.eduquiz.Entities.Category;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.Services.QuestionService;
import is.hi.hbv501g.eduquiz.Services.QuizService;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class SearchController {
    private QuizService quizService;
    private UserService userService;

    @Autowired
    public SearchController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String getSearchPage(@ModelAttribute QuizSearchDto quizSearchDto, Model model) {

        return "search";
    }

    @RequestMapping(value="/search", method = RequestMethod.POST, params={"keyword"})
    public String searchBySearchDto(QuizSearchDto quizSearchDto, Model model) {

        quizSearchDto.setKeyword((quizSearchDto.getKeyword() == null) ? "" : quizSearchDto.getKeyword());

        ArrayList<Quiz> results = quizService.findByKeywordAndFilterByCategory(quizSearchDto);

        if (!results.isEmpty()) {
            model.addAttribute("quizzes", results);
        }

        model.addAttribute("oldkeyword", quizSearchDto.getKeyword());
        model.addAttribute("lastselected", quizSearchDto.getCategory().getDisplayName());

       /* String path = "redirect:/search";

        return path;*/

        return "search";
    }

    @RequestMapping(value="/search", method = RequestMethod.POST, params={"search-by-keyword"})
    public String searchByTitleOrDescription(@RequestParam(value="search-by-keyword") String keyword, Model model) {

        ArrayList<Quiz> results = quizService.findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(keyword, keyword);

        if (!results.isEmpty()) {
            model.addAttribute("quizzes", results);
        }

        model.addAttribute("oldkeyword", keyword);

        return "search";

        /*String path = "redirect:/search";

        return path;*/
    }

    @RequestMapping(value="/find-by-category/{category}", method = RequestMethod.GET)
    public String get(@PathVariable(value="category") String category, Model model) {

        String capsCategory = category.toUpperCase();

        if (!Category.contains(capsCategory)) {
            return "search";
        }

        int indexForCategory = Category.valueOf(capsCategory).ordinal();

        ArrayList<Quiz> results = quizService.findQuizByCategory(indexForCategory);

        if (!results.isEmpty()) {
            model.addAttribute("quizzes", results);
        }

        return "search";
    }
}
