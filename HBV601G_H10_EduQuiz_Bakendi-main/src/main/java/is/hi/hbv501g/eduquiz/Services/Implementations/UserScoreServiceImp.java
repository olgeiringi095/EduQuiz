package is.hi.hbv501g.eduquiz.Services.Implementations;

import is.hi.hbv501g.eduquiz.DTO.QuizFormDto;
import is.hi.hbv501g.eduquiz.DTO.UserFormDto;
import is.hi.hbv501g.eduquiz.Entities.*;
import is.hi.hbv501g.eduquiz.Repositories.UserRepository;
import is.hi.hbv501g.eduquiz.Repositories.UserScoreRepository;
import is.hi.hbv501g.eduquiz.Services.UserScoreService;
import is.hi.hbv501g.eduquiz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserScoreServiceImp implements UserScoreService {
    UserScoreRepository repository;

    @Autowired
    public UserScoreServiceImp(UserScoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserScore save(UserScore user) {
        return repository.save(user);
    }

    @Override
    public void delete(UserScore user) {
        repository.delete(user);
    }

    @Override
    public List<UserScore> findAll() { return repository.findAll(); };
}