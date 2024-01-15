package is.hi.hbv501g.eduquiz.Services;

import is.hi.hbv501g.eduquiz.Entities.Answer;

import java.util.ArrayList;
import java.util.Optional;

public interface AnswerService {
    Answer save(Answer answer);
    void delete(Answer answer);
    ArrayList<Answer> findAll();
    Optional<Answer> findById(long id);
}