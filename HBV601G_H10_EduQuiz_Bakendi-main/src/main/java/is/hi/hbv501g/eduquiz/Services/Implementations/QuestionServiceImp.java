package is.hi.hbv501g.eduquiz.Services.Implementations;

import is.hi.hbv501g.eduquiz.Entities.Question;
import is.hi.hbv501g.eduquiz.Repositories.QuestionRepository;
import is.hi.hbv501g.eduquiz.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImp implements QuestionService {
    QuestionRepository repository;

    @Autowired
    public QuestionServiceImp(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Question question) {
        repository.delete(question);
    }

    @Override
    public Optional<Question> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Question save(Question question) {
        return repository.save(question);
    }
}