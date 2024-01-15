package is.hi.hbv501g.eduquiz.Services.Implementations;

import is.hi.hbv501g.eduquiz.Entities.Answer;
import is.hi.hbv501g.eduquiz.Repositories.AnswerRepository;
import is.hi.hbv501g.eduquiz.Services.AnswerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AnswerServiceImp implements AnswerService {

    AnswerRepository answerRepository;

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    @Override
    public ArrayList<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Optional<Answer> findById(long id) {
        return answerRepository.findById(id);
    }
}