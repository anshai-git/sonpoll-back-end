package com.sonpoll.oradea.sonpoll;

import com.sonpoll.oradea.sonpoll.poll.repository.PollRepository;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan({ "com.sonpoll.oradea.sonpoll" })
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
        System.out.println("[SONPOLL] Application Startup");
    }
}
