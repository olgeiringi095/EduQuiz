package is.hi.hbv501g.eduquiz.Entities;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.text.SimpleDateFormat;

@Entity
public class Quiz {

    @Id
    @Column(name = "quizId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private Type type;

    private double averageScore;

    private String description;

    private long totalPlays = 0;

    //@JsonIgnore
    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "quiz")
    Set<UserScore> scores;


    private Date creation;

    //quiz er tilviksbreyta í Question
    @JsonManagedReference
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();


    @ElementCollection(targetClass=Category.class)
    @Column(name="category", nullable=false)
    @CollectionTable(name="quiz_categories", joinColumns= {@JoinColumn(name="quiz_id")})
    public Set<Category> categories;

    //Setur sjálfvirkt inn dagsetningu
    @PrePersist
    protected void onCreate() {
        creation = new Date();
    }


    public Quiz() {
    }

    public Quiz(long id, String title, String description, Date creation, HashSet<Category> categories, ArrayList<Question> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creation = creation;
        this.categories = categories;
        this.questions = questions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void addQuestion(Question question) {
        question.setQuiz(this);
        this.getQuestions().add(question);
    }

    @Override
    public String toString() {
        return this.title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public long getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(long totalPlays) {
        this.totalPlays = totalPlays;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
