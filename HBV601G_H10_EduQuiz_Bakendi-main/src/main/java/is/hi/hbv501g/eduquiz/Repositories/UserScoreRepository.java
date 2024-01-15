package is.hi.hbv501g.eduquiz.Repositories;

import is.hi.hbv501g.eduquiz.Entities.User;
import is.hi.hbv501g.eduquiz.Entities.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    UserScore save(UserScore user);
    void delete(UserScore user);
}