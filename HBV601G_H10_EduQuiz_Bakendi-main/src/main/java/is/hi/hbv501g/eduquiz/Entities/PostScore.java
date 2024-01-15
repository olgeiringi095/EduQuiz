package is.hi.hbv501g.eduquiz.Entities;

public class PostScore {
    private int score;
    private long id;

    public PostScore(int score, long id) {
        this.score = score;
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
