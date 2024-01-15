package is.hi.hbv501g.eduquiz.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Column(name = "userId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true)
    private String userName;

    @JsonIgnore
    private String password;

    @Column(unique=true)
    private String email;

    @Column(columnDefinition = "integer default 0")
    private long score = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Quiz> quizzesByUser = new ArrayList<>();

    @ElementCollection(targetClass=Category.class)
    @Column(name="category", nullable=false)
    @CollectionTable(name="user_sub", joinColumns= {@JoinColumn(name="user_id")})
    public Set<Category> subscription;


    @OneToMany(mappedBy = "user")
    Set<UserScore> scores;

    

    public User(long id, String userName, String password, String email, Set<UserScore> scores) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.scores = scores;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {this.email = email; }

    public void setScore(long score) { this.score = score; }

    public long getScore() { return score; }

    public String getEmail() {return email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Quiz> getQuizzesByUser() {
        return quizzesByUser;
    }

    public void setQuizzesByUser(List<Quiz> quizzesByUser) {
        this.quizzesByUser = quizzesByUser;
    }

    public Set<Category> getSubscription() {
        return subscription;
    }

    public void setSubscription(Set<Category> subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return userName;
    }
}
