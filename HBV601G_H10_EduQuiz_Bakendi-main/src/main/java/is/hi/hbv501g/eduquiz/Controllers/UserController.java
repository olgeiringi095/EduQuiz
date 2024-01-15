package is.hi.hbv501g.eduquiz.Controllers;

import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.Entities.User;
import is.hi.hbv501g.eduquiz.Services.QuizService;
import is.hi.hbv501g.eduquiz.Services.QuestionService;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UserController {

    private QuizService quizService;
    private UserService userService;
    private QuestionService questionService;

    @Autowired
    public UserController(QuizService quizService, QuestionService questionService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @RequestMapping("/")
    public String Home(HttpSession session, Model model) {

        model.addAttribute("top5playedquizzes", quizService.findTop5MostPlayed());
        model.addAttribute("quizzes", quizService.findTop5ByOrderByCreationDesc());
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        if(sessionUser != null) {
            long sessionid = userService.findIdByUserName(sessionUser.getUserName()).getId();
            model.addAttribute("loggedinuser", sessionUser);
            model.addAttribute("loggedinid", sessionid);
            return "main";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGET(@ModelAttribute User user) { return "register"; }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPOST(@Valid User user, BindingResult result, Model model) {
      if(result.hasErrors()) {
          return "register";
        }
        if(userService.findByUserName(user.getUserName()) == null) {
          userService.save(user);
        }
        return "redirect:/login?success";
      }

      @RequestMapping(value = "/login", method = RequestMethod.GET)
      public String loginGET(@ModelAttribute User user) {
        return "LoginPage";
      }

      @RequestMapping(value = "/login", method = RequestMethod.POST)
      public String loginPOST(@Valid User user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
          return "redirect:/login?error";
        }
        User exists = userService.login(user);
        if(exists != null){
          session.setAttribute("LoggedInUser", user);
          User sessionUser = (User) session.getAttribute("LoggedInUser");
          long sessionid = userService.findIdByUserName(sessionUser.getUserName()).getId();
          session.setAttribute("LoggedInId", sessionid);
          return "redirect:/";
        }
        return "redirect:/login?error";
      }

    //Útskráning notanda
    @RequestMapping(value = "/logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value="/notandi/{id}")
    public String notandi(@PathVariable("id") long id, HttpSession session, Model model) {
        model.addAttribute("quizbyuser", quizService.findQuizByUserId(id));
        model.addAttribute("idusername", userService.findUserNameById(id));
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        long sessionId = (long) session.getAttribute("LoggedInId");

        if(sessionUser != null) {
            model.addAttribute("loggedinuser", sessionUser);
            model.addAttribute("loggedinid", sessionId);
            return "notandi";
        }
        return "LoginPage";

    }

}
