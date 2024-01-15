package is.hi.hbv501g.eduquiz.Services;

import is.hi.hbv501g.eduquiz.DTO.UserFormDto;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.Entities.User;
import is.hi.hbv501g.eduquiz.Entities.UserScore;

import java.util.ArrayList;
import java.util.List;

public interface UserScoreService {
    UserScore save(UserScore user);
    void delete(UserScore user);
    List<UserScore> findAll();
}