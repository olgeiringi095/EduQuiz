package is.hi.hbv501g.eduquiz.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
class UserScoreKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "quiz_id")
    Long quizId;

    public UserScoreKey() {

    }
    public UserScoreKey(Long userId, Long quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
@Entity
@Table(name = "userscore")
public class UserScore {
    @EmbeddedId
    UserScoreKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    Quiz quiz;

    private int score;
    public UserScore() {

    }
    public UserScore(UserScoreKey id, User user, Quiz quiz, int score) {
        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.score = score;
    }

    public UserScoreKey getId() {
        return id;
    }

    public void setId(UserScoreKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setKey(long a, long b) {
        UserScoreKey nytt = new UserScoreKey(a,b);
        setId(nytt);
    }
}
