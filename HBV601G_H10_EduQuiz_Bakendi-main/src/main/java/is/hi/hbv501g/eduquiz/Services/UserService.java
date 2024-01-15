package is.hi.hbv501g.eduquiz.Services;

import is.hi.hbv501g.eduquiz.DTO.UserFormDto;
import is.hi.hbv501g.eduquiz.Entities.Quiz;
import is.hi.hbv501g.eduquiz.Entities.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    User mapToUser(UserFormDto userFormDto);
    UserFormDto mapToUserFormDto(User user);
    User findByUserName(String userName);
    User findById(long id);
    User findUserNameById(long id);
    User findByEmail(String email);
    User findIdByUserName(String UserName);
    User login(User user);
    void addQuizToUser(User user, Quiz quiz);
    List<User> findTop10Users();
    List<User> findTopUsers();
}