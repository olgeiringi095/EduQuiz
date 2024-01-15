package is.hi.hbv501g.eduquiz.Repositories;

import is.hi.hbv501g.eduquiz.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question save(Question question);
    void delete(Question question);
    Optional<Question> findById(long id);
}
