package com.sonpoll.oradea.sonpoll;

import com.sonpoll.oradea.sonpoll.poll.model.Option;
import com.sonpoll.oradea.sonpoll.poll.model.Poll;
import com.sonpoll.oradea.sonpoll.poll.model.Question;
import com.sonpoll.oradea.sonpoll.poll.repository.PollRepository;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.sonpoll.oradea.sonpoll"})
@EnableMongoRepositories
public class SonpollApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PollRepository pollRepository;

    public static void main(String[] args) {
        SpringApplication.run(SonpollApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n DB Users: \n ");
        userRepository.findAll().forEach(user -> System.out.println(user.toString()));
        System.out.println("\n DB Tokens: \n ");
//        userTokenRepository.findAll().forEach(user -> System.out.println(user.toString()));

//        pollRepository.save(initPollDetails());
        System.out.println("\n DB POLLS: \n ");
        pollRepository.findAll().forEach(poll -> System.out.println(poll.toString()));
        Optional<Poll> pollByOwner = pollRepository.findByOwner("63cd118eb150044b7fe74729");
    }

    private static Poll initPollDetails() {
        List<Question> questions = new ArrayList<>();
        Question q1 = Question.builder()
                .title("first q")
                .isMultiselectAllowed(true)
                .description("first desc")
                .options(Arrays.asList(new Option("luni"), new Option("marti"), new Option("miercuri")))
                .build();
        Question q2 = Question.builder()
                .title("sec q")
                .isMultiselectAllowed(true)
                .description("sec desc")
                .options(Arrays.asList(new Option("luni"), new Option("marti"), new Option("miercuri")))
                .build();
        questions.addAll(List.of(q1, q2));
        return Poll.builder()
                .owner("63cd118eb150044b7fe74729")
                .publicResults(true)
                .title("first poll")
                .questions(questions)
                .build();
    }
}
