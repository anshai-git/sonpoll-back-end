package com.sonpoll.oradea.sonpoll.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonpoll.oradea.sonpoll.poll.repository.PollRepository;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;

@RequestMapping("/integrity")
@RestController
public class DataIntegrityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @RequestMapping("/user")
    public IntegrityCheckResult verifyUserCollection() {
        final List<User> users = userRepository.findAll();
        return IntegrityCheckResult.builder().build();
    }

}
