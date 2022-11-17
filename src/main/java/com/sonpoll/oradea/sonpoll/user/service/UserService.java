package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.mail.EmailService;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.model.UserToken;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import com.sonpoll.oradea.sonpoll.user.repository.UserTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepo;
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

    public void sendResetPassEmail(final ResetPasswordRequestDTO request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isPresent()) {
            Optional<UserToken> userToken = userTokenRepo.findByUserIdAndToken(user.get().getId(), request.getToken());
            if (userToken.isPresent()) {
                if (userToken.get().isActive()) {
                    final String resetLink = new StringBuilder()
                            .append("localhost:8080/auth/resetPasswrod?userId=")
                            .append(user.get().getId())
                            .append("&&token=")
                            .append(userToken.get().getToken())
                            .toString();
                    emailSender.sendEmail(user.get().getEmail(), resetLink);
                } else {
//                UserToken newToken = new UserToken(user.get().getId())
                    // TODO Flisc 17.11.2022 generate new token and send the like with it
                }
            }
        } else {
            // TODO Flisc 17.11.2022 user not found
        }
    }
}
