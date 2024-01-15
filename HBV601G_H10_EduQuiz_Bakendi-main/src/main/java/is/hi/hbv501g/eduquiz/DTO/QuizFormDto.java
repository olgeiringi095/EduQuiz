package is.hi.hbv501g.eduquiz.DTO;

import is.hi.hbv501g.eduquiz.Entities.Category;
import is.hi.hbv501g.eduquiz.Entities.Type;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuizFormDto {

    @Size(min = 5, max = 50, message = "Vinsamlegast fylltu inn titil sem inniheldur 5-50 stafi")
    @NotEmpty(message = "Titill má ekki vera auður")
    private String title;

    @Size(min = 5, max = 255, message = "Vinsamlegast fylltu inn lýsingu sem inniheldur 5-255 stafi")
    @NotEmpty(message = "Lýsing má ekki vera auð")
    private String description;

    private Type type;

    private long totalPlays;

    public Set<Category> categories;

    private List<List<@NotEmpty(message="Svar má ekki vera autt") String>> listOfAnswers = new ArrayList<>();

    @Valid
    @NotEmpty //@Valid erfir niður i stök listans
    private List<@NotEmpty(message="Spurning má ekki vera auð") String> questions = new ArrayList<>();

    public List<String> getQuestions() {
        return questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuizFormDto() {}

    public QuizFormDto(@NotEmpty String title, @NotEmpty String description, @Valid @NotEmpty List<@NotEmpty String> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<List<String>> getListOfAnswers() {
        return listOfAnswers;
    }

    public void setListOfAnswers(List<List<String>> listOfAnswers) {
        this.listOfAnswers = listOfAnswers;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(long totalPlays) {
        this.totalPlays = totalPlays;
    }


}
