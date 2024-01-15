package is.hi.hbv501g.eduquiz.Services;

import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.DTO.QuizSearchDto;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.Entities.User;

import java.util.ArrayList;
import java.util.Optional;

public interface QuizService {
    Quiz save(Quiz quiz);
    void delete(Quiz quiz);
    ArrayList<Quiz> findAll();
    ArrayList<Quiz> findQuizByUserId(long id);
    Optional<Quiz> findById(long id);
    Quiz mapToQuiz(QuizFormDto quizFormDto);
    QuizFormDto mapToQuizFormDto(Quiz quiz);
    void addQuestionsAndAnswers(QuizFormDto quizFormDto, int questionsToAdd, int numberOfAnswers);
    void randomizeAnswers(Quiz quiz);
    ArrayList<Quiz> findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String title, String Description);

    ArrayList<Quiz> findQuizByCategory(int categoryIndex);

    ArrayList<Quiz> findByKeywordAndFilterByCategory(QuizSearchDto quizSearchDto);

    ArrayList<Quiz> findTop5ByOrderByCreationDesc();

    void setCreator(Quiz quiz, User user);

    void incrementTotalPlays(Quiz quiz);

    ArrayList<Quiz> findTop5MostPlayed();

    void calcAverageScore(Quiz quiz, int lastCorrectAnswers);

    void setQuizFormDtoType(int typeOrdinal, QuizFormDto quizFormDto);

    void setInfoFromOldQuiz(Quiz oldQuiz, Quiz newQuiz);
}
