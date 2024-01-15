package is.hi.hbv501g.eduquiz.Services.Implementations;

import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.DTO.UserFormDto;
import is.hi.hbv501g.eduquiz.Entities.*;
import is.hi.hbv501g.eduquiz.Repositories.UserRepository;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByEmail(String email) { return repository.findByEmail(email); }

    @Override
    public User findUserNameById(long id) { return repository.findUserNameById(id); }

    @Override
    public User findIdByUserName(String UserName) { return repository.findIdByUserName(UserName); }


    @Override
    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public User findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findTop10Users() {
        return repository.findTop10Users();
    }

    @Override
    public List<User> findTopUsers() { return repository.findTopUsers(); }

    @Override
    public User login(User user) {
        User exists = findByUserName(user.getUserName());
        if(exists != null){
            if(exists.getPassword().equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    @Override
    public void addQuizToUser(User user, Quiz quiz) {
        user.getQuizzesByUser().add(quiz);
    }

    @Override
    public User mapToUser(UserFormDto userFormDto) {
        User user = new User();

        user.setUserName(userFormDto.getUsername());
        user.setPassword(userFormDto.getPassword());
        user.setEmail(userFormDto.getEmail());

        user.setScore(0);
        user.setQuizzesByUser(null);
        user.setSubscription(null);

        return user;
    }

    @Override
    public UserFormDto mapToUserFormDto(User user) {
        UserFormDto userFormDto = new UserFormDto();

        userFormDto.setUsername(user.getUserName());
        userFormDto.setPassword(user.getPassword());
        userFormDto.setEmail(user.getEmail());

        /*
        Gæti þurft að bæta við  score, quiz by user og subscription.
        */
        return userFormDto;
    }
}