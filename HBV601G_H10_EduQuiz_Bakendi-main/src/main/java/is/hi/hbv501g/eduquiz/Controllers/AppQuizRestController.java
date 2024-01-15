package is.hi.hbv501g.eduquiz.Controllers;

import is.hi.hbv501g.eduquiz.DTO.UserFormDto;
import is.hi.hbv501g.eduquiz.Entities.*;

import is.hi.hbv501g.eduquiz.DTO.QuizSearchDto;
import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.DTO.Response;
import is.hi.hbv501g.eduquiz.Services.QuizService;
import is.hi.hbv501g.eduquiz.Services.UserScoreService;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;



@RestController
@RequestMapping("/api")
public class AppQuizRestController {
    private QuizService quizService;
    private UserService userService;
    private UserScoreService userScoreService;

    @Autowired
    public AppQuizRestController(QuizService quizService, UserService userService, UserScoreService userScoreService) {
        this.quizService = quizService;
        this.userService = userService;
        this.userScoreService = userScoreService;
    }

    @GetMapping(value = "/leaderboard")
    public List<User> leaderboard() {
        return userService.findTop10Users();
    }

    @GetMapping(value = "/leaderboard/{username}")
    public int userRank(@PathVariable("username") String userName) {
        User user;
        try {
            user = userService.findByUserName(userName);
            List<User> listi = userService.findTopUsers();
            for(int i = 0; i < listi.size(); i++) {
                if(listi.get(i).getUserName() == user.getUserName()) {
                    return i+1;
                }
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }


    @GetMapping(value = "/play/quiz/{id}")
    public List<Quiz> getByQuizId(@PathVariable("id") long id, Model model) {

        Quiz quiz = quizService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz id"));

        quizService.randomizeAnswers(quiz);

        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(quiz);

        return quizzes;
    }

    @GetMapping(value = "/all/quizzes")
    public Response getAllQuizzes() {
        List<Quiz> list = quizService.findAll();
        return new Response("Done", list);
    }
    
    @GetMapping(value = "/get/top5")
    public List<Quiz> getTop5Quizzes() {
        return quizService.findTop5MostPlayed();
    }

    @GetMapping(value = "/get/newest5")
    public List<Quiz> getNewest5Quizzes() {
        return quizService.findTop5ByOrderByCreationDesc();
    }

    @GetMapping(value = "/user/{id}/quizzes")
    public List<Quiz> getUserQuizzes(HttpSession session) {
        String currentUserName = ((User)session.getAttribute("LoggedInUser")).getUserName();
        long currentId = userService.findByUserName(currentUserName).getId();
        return quizService.findQuizByUserId(currentId);
    }

    @GetMapping(value = "/user/quizzes/{id}")
    public List<Quiz> getSelectedUserQuizzes(@PathVariable("id") long id, HttpSession session) {
        return quizService.findQuizByUserId(id);
    }

    @GetMapping(value = "/user/{id}")
    public User getUserInfo(@PathVariable("id") long id, HttpSession session) {
        return userService.findById(id);
    }



    @PostMapping(value = "/test")
    UserScore test(@RequestBody PostScore postScore) {
        String currUsername = "nem64"; //((User)session.getAttribute("LoggedInUser")).getUserName();
        User currentUser = userService.findByUserName(currUsername);
        Quiz quiz = quizService.findById(postScore.getId()).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz id"));
        UserScore nytt = new UserScore();
        nytt.setKey(currentUser.getId(), quiz.getId());
        nytt.setUser(currentUser);
        nytt.setQuiz(quiz);
        nytt.setScore(postScore.getScore());
        userScoreService.save(nytt);
        return nytt;
    }
    @PostMapping(value = "/play/gameover")
    UserScore finished(@RequestBody PostScore postScore, HttpSession session) {
        String currUsername = ((User)session.getAttribute("LoggedInUser")).getUserName();
        User currentUser = userService.findByUserName(currUsername);
        Quiz quiz = quizService.findById(postScore.getId()).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz id"));
        UserScore nytt = new UserScore();
        nytt.setKey(currentUser.getId(), quiz.getId());
        nytt.setUser(currentUser);
        nytt.setQuiz(quiz);
        nytt.setScore(postScore.getScore());
        quizService.incrementTotalPlays(quiz);
        quizService.calcAverageScore(quiz, postScore.getScore());
        currentUser.setScore(currentUser.getScore()+postScore.getScore());
        userService.save(currentUser);
        quizService.save(quiz);
        userScoreService.save(nytt);
        return nytt;
    }



    @PostMapping(value = "/create/quiz")
    Quiz nyttQuiz(@RequestBody QuizFormDto nyttQuiz, HttpSession session) {
        String currentUserName = ((User)session.getAttribute("LoggedInUser")).getUserName();
        User currentUser = userService.findByUserName(currentUserName);

        Quiz quiz = quizService.mapToQuiz(nyttQuiz);

        userService.addQuizToUser(currentUser, quiz);
        quizService.setCreator(quiz, currentUser);
        userService.save(currentUser);

        return quiz;
    }
    
    @PostMapping(value = "/create/user")
    User nyruser(@RequestBody @Valid UserFormDto nyrNot) {
        User notandi = userService.mapToUser(nyrNot);
        if(userService.findByUserName(notandi.getUserName()) == null && userService.findByEmail(notandi.getEmail()) == null) {
            return userService.save(notandi);
        }
        //Skilar null því tveir geta ekki heitið það sama
        return null;
    }
    
    @PostMapping(value = "/login")
    public User login(@RequestBody UserFormDto userFormDto, HttpSession session){
        User exists = userService.findByUserName(userFormDto.getUsername());
        if (exists == null || !exists.getPassword().equals(userFormDto.getPassword())) {
            return null;
        }

        session.setAttribute("LoggedInUser", exists);
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        long sessionid = userService.findIdByUserName(sessionUser.getUserName()).getId();
        session.setAttribute("LoggedInId", sessionid);
        
        exists.setPassword("");
        return exists;
    }
    
    @PostMapping(value = "/search/quiz")
    List<Quiz> searchQuizzes(@RequestBody QuizSearchDto[] list) {
        QuizSearchDto quizSearchDto = list[0];
        quizSearchDto.setKeyword((quizSearchDto.getKeyword() == null) ? "" : quizSearchDto.getKeyword());

        return quizService.findByKeywordAndFilterByCategory(quizSearchDto);
    }
    @PostMapping(value = "/user/subscribe")
    User nyttSub(@RequestBody Subscription sub) {
        if(!Category.contains(sub.getSubscription())) {
            return null;
        }
        Category cat = Category.valueOf(sub.getSubscription());
        User user = userService.findByUserName(sub.getUsername());

        if(user == null) {
            return null;
        }
        Set set = user.getSubscription();
        set.add(cat);
        System.out.println(sub.getUsername());
        user.setSubscription(set);
        userService.save(user);
        return user;
    }
    @PostMapping(value = "/user/unsubscribe")
    User unsub(@RequestBody Subscription sub)  {
        if(!Category.contains(sub.getSubscription())) {
            return null;
        }
        Category cat = Category.valueOf(sub.getSubscription());
        User user = userService.findByUserName(sub.getUsername());

        if(user == null) {
            return null;
        }

        Set set = user.getSubscription();
        set.remove(cat);
        System.out.println(sub.getUsername());
        user.setSubscription(set);
        userService.save(user);
        return user;
    }
    @PostMapping(value = "/user/subbox")
    Set<Category> subbox(@RequestBody Subscription sub) {
        User user = userService.findByUserName(sub.getUsername());
        if(user == null) {
            return null;
        }
        return user.getSubscription();
    }
    @RequestMapping(value="/delete/quiz/{id}", method = RequestMethod.GET)
    public String deleteQuiz(@PathVariable("id") long id, HttpSession session) {
        String currentUserName = ((User)session.getAttribute("LoggedInUser")).getUserName();
        User currentUser = userService.findByUserName(currentUserName);

        if (currentUser == null) {
            return "User must be logged in";
        }

        Quiz quiz = quizService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid quiz ID"));

        if (currentUser.getId() != quiz.getCreator().getId()) {
            return "User does not have permission";
        }
        List<UserScore> userScoreList = userScoreService.findAll();
        for(int i = 0; i < userScoreList.size(); i++) {
            if(userScoreList.get(i).getQuiz().getId() == id) {
                userScoreService.delete(userScoreList.get(i));
            }
        }
        quizService.delete(quiz);
        return "Quiz was deleted";
    }
}
