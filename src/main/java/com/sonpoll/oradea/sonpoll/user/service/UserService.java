package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.mail.EmailService;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailSender;

//    @Autowired
//    public UserService(UserRepository userRepository, EmailService emailSender) {
//        this.userRepository = userRepository;
//        this.emailSender = emailSender;
//    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void sendResetPassEmail()  {
        emailSender.sendEmail("fliscadrian23@gmail.com");
//        return;
    }
}
