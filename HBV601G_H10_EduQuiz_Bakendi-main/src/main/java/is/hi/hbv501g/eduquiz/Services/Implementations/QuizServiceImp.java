package is.hi.hbv501g.eduquiz.Services.Implementations;

import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.DTO.QuizSearchDto;
import is.hi.hbv501g.eduquiz.Entities.*;
import is.hi.hbv501g.eduquiz.Repositories.QuizRepository;
import is.hi.hbv501g.eduquiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class QuizServiceImp implements QuizService {

    QuizRepository repository;

    @Autowired
    public QuizServiceImp(QuizRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quiz save(Quiz quiz) {
        return repository.save(quiz);
    }

    @Override
    public void delete(Quiz quiz) {
        repository.delete(quiz);
    }

    @Override
    public ArrayList<Quiz> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Quiz> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Quiz mapToQuiz(QuizFormDto quizFormDto) {
        Quiz quiz = new Quiz();

        quiz.setDescription(quizFormDto.getDescription());
        quiz.setTitle(quizFormDto.getTitle());

        quiz.setCategories(quizFormDto.getCategories());
        quiz.getCategories().add(Category.MULTIPLECHOICE);
        if (quizFormDto.getType() == Type.questionsAreImageUrl) {
            quiz.getCategories().add(Category.PICTURE);
        }

        if (quizFormDto.getListOfAnswers() != null &&
                !quizFormDto.getListOfAnswers().isEmpty()) {

            int numberOfAnswers = quizFormDto.getListOfAnswers().get(0).size();

            for (int i = 0; i < quizFormDto.getQuestions().size(); i++) {
                Question currentQuestion = new Question(quizFormDto.getQuestions().get(i), quiz);
                quiz.getQuestions().add(currentQuestion);
                for (int j = 0; j < numberOfAnswers; j++) {
                    Answer currentAnswer = new Answer(quizFormDto.getListOfAnswers().get(i).get(j), currentQuestion);
                    quiz.getQuestions().get(i).getAnswers().add(currentAnswer);
                }
            }

            //Fyrsta stakið í forminu er rétta svarið
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                quiz.getQuestions().get(i).getAnswers().get(0).setCorrect(true);
            }
        }

        quiz.setType(quizFormDto.getType());

        quiz.setTotalPlays(quizFormDto.getTotalPlays());

        return quiz;
    }

    @Override
    public QuizFormDto mapToQuizFormDto(Quiz quiz) {
        QuizFormDto quizFormDto = new QuizFormDto();

        quizFormDto.setDescription(quiz.getDescription());
        quizFormDto.setTitle(quiz.getTitle());

        quizFormDto.setCategories(quiz.getCategories());
        if (quiz.getType() == Type.questionsAreImageUrl) {
            quizFormDto.getCategories().add(Category.PICTURE);
        }

        if (quiz.getQuestions() != null &&
                !quiz.getQuestions().isEmpty() &&
                quiz.getQuestions().get(0).getAnswers() != null &&
                !quiz.getQuestions().get(0).getAnswers().isEmpty()) {

            int numberOfAnswers = quiz.getQuestions().get(0).getAnswers().size();
            int numberOfQuestions = quiz.getQuestions().size();

            for (int i = 0; i < numberOfQuestions; i++) {
                quizFormDto.getQuestions().add(quiz.getQuestions().get(i).getQuestion());
                quizFormDto.getListOfAnswers().add(new ArrayList<String>());

                for (int j = 0; j < numberOfAnswers; j++) {
                    String currentAnswer = quiz.getQuestions().get(i).getAnswers().get(j).getAnswer();
                    quizFormDto.getListOfAnswers().get(i).add(currentAnswer);
                }
            }
        }

        quizFormDto.setType(quiz.getType());
        quizFormDto.setTotalPlays(quiz.getTotalPlays());

        return quizFormDto;
    }

    @Override
    public void addQuestionsAndAnswers(QuizFormDto quizFormDto, int questionsToAdd, int numberOfAnswers) {

        for (int i = 0; i < questionsToAdd; i++) {
            quizFormDto.getQuestions().add("");
            quizFormDto.getListOfAnswers().add(new ArrayList<String>());
            for (int j = 0; j < numberOfAnswers; j++) {
                int lastIndex = quizFormDto.getQuestions().size() -1;
                quizFormDto.getListOfAnswers().get(lastIndex).add("");
            }
        }

    }

    @Override
    public void randomizeAnswers(Quiz quiz) {
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Collections.shuffle(quiz.getQuestions().get(i).getAnswers());
            System.out.println("hæ");
        }
    }

    @Override
    public ArrayList<Quiz> findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String title, String Description) {
        if (title.isEmpty()) {
            System.out.println("var empty");
            return repository.findAll();
        }
        return repository.findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(title, Description);
    }

    @Override
    public ArrayList<Quiz> findQuizByCategory(int categoryIndex) {
        return repository.findQuizByCategory(categoryIndex);
    }

    @Override
    public ArrayList<Quiz> findByKeywordAndFilterByCategory(QuizSearchDto quizSearchDto) {
        String keyword = quizSearchDto.getKeyword();

        ArrayList<Quiz> searchResults = findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(keyword, keyword);

        if (searchResults != null && !searchResults.isEmpty()) {
            searchResults.removeIf(quiz -> quiz.getCategories() != null && !quiz.getCategories().contains(quizSearchDto.getCategory()));
        }

        return searchResults;
    }

    @Override
    public ArrayList<Quiz> findTop5ByOrderByCreationDesc() {
        return repository.findTop5ByOrderByCreationDesc();
    }

    @Override
    public void setCreator(Quiz quiz, User user) {
        quiz.setCreator(user);
    }

    @Override
    public ArrayList<Quiz> findQuizByUserId(long id) { return repository.findQuizByUserId(id); }

    @Override
    public void incrementTotalPlays(Quiz quiz) {
        quiz.setTotalPlays(quiz.getTotalPlays() + 1);
    }

    @Override
    public ArrayList<Quiz> findTop5MostPlayed() {
        return repository.findTop5MostPlayed();
    }

    @Override
    public void calcAverageScore(Quiz quiz, int lastCorrectAnswers) {
        double lastGameAvg = ((double)lastCorrectAnswers / (double)(quiz.getQuestions().size())) * 100;
        if (quiz.getTotalPlays() == 0) {
            quiz.setAverageScore(lastGameAvg);
            return;
        }
        //https://math.stackexchange.com/questions/22348/how-to-add-and-subtract-values-from-an-average

        double oldAvg = quiz.getAverageScore();

        quiz.setAverageScore( oldAvg + ( (lastGameAvg - oldAvg ) / (quiz.getTotalPlays()) ));
    }

    @Override
    public void setQuizFormDtoType(int typeOrdinal, QuizFormDto quizFormDto) {
        quizFormDto.setType(Type.values()[typeOrdinal]);
    }

    @Override
    public void setInfoFromOldQuiz(Quiz oldQuiz, Quiz newQuiz) {
        newQuiz.setTotalPlays(oldQuiz.getTotalPlays());
        if (oldQuiz.getType() == Type.questionsAreImageUrl) {
            newQuiz.getCategories().add(Category.PICTURE);
        }
        newQuiz.setAverageScore(oldQuiz.getAverageScore());
    }
}
