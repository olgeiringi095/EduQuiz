package is.hi.hbv501g.eduquiz.Services;

import is.hi.hbv501g.eduquiz.Entities.Question;

import java.util.Optional;

public interface QuestionService {
    Question save(Question question);
    void delete(Question question);
    Optional<Question> findById(long id);
}
