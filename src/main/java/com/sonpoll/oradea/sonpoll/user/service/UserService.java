package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.mail.EmailService;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.model.UserToken;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import com.sonpoll.oradea.sonpoll.user.repository.UserTokenRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepo;
    private final EmailService emailSender;

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

    public void sendResetPassEmail(final String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        String resetLink, token;
        if (user.isPresent()) {
            Optional<UserToken> userToken = userTokenRepo.findByUserId(user.get().getId());
            if (userToken.isPresent() && userToken.get().isActive()) {
                token = userToken.get().getToken();
                userTokenRepo.save(userToken.get());
            } else {
                LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES));
                UserToken newToken = new UserToken(user.get().getId(), UUID.randomUUID().toString(), false, expirationDate);
                userTokenRepo.save(newToken);
                token = newToken.getToken();
            }
            resetLink = new StringBuilder()
                    .append("localhost:4200/auth/resetPasswrod?userId=")
                    .append(user.get().getId())
                    .append("&&token=")
                    .append(token)
                    .toString();
            emailSender.sendEmail(userEmail, resetLink);
        } else {
            logger.error("User not found: userId: {}", userEmail);
        }
    }

    public void updatePasswordForUser(final ResetPasswordRequestDTO resetPasswordRequest) {
        Optional<UserToken> token = userTokenRepo.findByUserIdAndToken(resetPasswordRequest.getUserId(), resetPasswordRequest.getToken());
        final PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (token.isPresent() && token.get().isActive()) {
            Optional<User> user = userRepository.findById(resetPasswordRequest.getUserId());
            if (user.isPresent()) {
                user.get().setPassword(encoder.encode(resetPasswordRequest.getNewPassword()));
                userRepository.save(user.get());
                token.get().setIsUsed(true);
                userTokenRepo.save(token.get());
            } else {
                logger.error("User not found with userId: {}", resetPasswordRequest.getUserId());
            }
        } else {
            logger.error("Token not found or already used :( ");
        }
    }
}
