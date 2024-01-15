package is.hi.hbv501g.eduquiz.Repositories;

import is.hi.hbv501g.eduquiz.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findByUserName(String userName);
    User findById(long id);
    User findByEmail(String email);
    User findUserNameById(long id);

    @Query(value="SELECT * FROM User u WHERE u.user_name =?1", nativeQuery = true)
    User findIdByUserName(String UserName);

    @Query(value="SELECT * FROM user ORDER BY score DESC LIMIT 10", nativeQuery = true)
    List<User> findTop10Users();

    @Query(value="SELECT * FROM user ORDER BY score DESC", nativeQuery = true)
    List<User> findTopUsers();
}