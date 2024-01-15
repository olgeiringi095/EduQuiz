package is.hi.hbv501g.eduquiz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.hbv501g.eduquiz.Entities.Answer;

import java.util.ArrayList;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer save(Answer answer);
    void delete(Answer answer);
    ArrayList<Answer> findAll();
    Optional<Answer> findById(long id);
}
