package is.hi.hbv501g.eduquiz.Entities;

import javax.persistence.Entity;

public class Subscription {
    private String username;
    private String subscription;

    public Subscription(String username, String subscription) {
        this.username = username;
        this.subscription = subscription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
