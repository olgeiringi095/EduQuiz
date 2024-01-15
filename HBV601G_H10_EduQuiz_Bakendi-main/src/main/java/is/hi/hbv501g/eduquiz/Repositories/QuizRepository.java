package is.hi.hbv501g.eduquiz.Repositories;

import is.hi.hbv501g.eduquiz.Entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz save(Quiz quiz);
    void delete(Quiz quiz);
    ArrayList<Quiz> findAll();
    Optional<Quiz> findById(long id);
    ArrayList<Quiz> findByTitleIgnoreCaseContainsOrDescriptionIgnoreCaseContains(String title, String Description);
    @Query(value ="SELECT * FROM Quiz q, Quiz_categories c WHERE c.category = ?1 AND q.quiz_id = c.quiz_id", nativeQuery = true)
    ArrayList<Quiz> findQuizByCategory(int categoryIndex);
    ArrayList<Quiz> findTop5ByOrderByCreationDesc();

    @Query(value="SELECT * FROM Quiz ORDER BY total_plays DESC LIMIT 5", nativeQuery = true)
    ArrayList<Quiz> findTop5MostPlayed();

    @Query(value="SELECT * FROM Quiz q, User u WHERE u.user_id = ?1 AND q.creator_user_id = ?1", nativeQuery = true)
    ArrayList<Quiz> findQuizByUserId(long id);
}
