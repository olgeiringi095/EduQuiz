package is.hi.hbv501g.eduquiz.DTO;

import is.hi.hbv501g.eduquiz.Entities.Type;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class QuizOptionsDto {

    @Min(1)
    @Max(50)
    private int numberOfQuestions = 1;

    @Min(2)
    @Max(8)
    private int numberOfAnswers = 2;

    private Type type;

    public QuizOptionsDto(int numberOfQuestions, int numberOfAnswers, Type type) {
        this.numberOfQuestions = numberOfQuestions;
        this.numberOfAnswers = numberOfAnswers;
        this.type = type;
    }

    public QuizOptionsDto() {
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}


