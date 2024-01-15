package is.hi.hbv501g.eduquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EduQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduQuizApplication.class, args);
    }

}