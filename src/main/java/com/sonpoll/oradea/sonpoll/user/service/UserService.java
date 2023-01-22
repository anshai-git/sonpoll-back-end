package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.mail.EmailService;
import com.sonpoll.oradea.sonpoll.user.model.AccesToken;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
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

    public CommonResponseDTO sendResetPassEmail(final String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        String resetLink, token;
        if (user.isPresent()) {
//            Optional<UserToken> accesToken = userTokenRepo.findByUserId(user.get().getId());
            AccesToken accesToken = user.get().getAccesToken();
            if (accesToken != null && accesToken.isActive()) {
                token = accesToken.getToken();
//                userTokenRepo.save(accesToken.get());
            } else {
                LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES));
//                UserToken newToken = new UserToken(user.get().getId(), UUID.randomUUID().toString(), false, expirationDate);
                AccesToken newToken = new AccesToken(UUID.randomUUID().toString(), false, expirationDate);
                user.get().setAccesToken(newToken);
                userRepository.save(user.get());
                token = newToken.getToken();
            }
            resetLink = new StringBuilder()
                    .append("localhost:4200/auth/resetPasswrod?userId=")
                    .append(user.get().getId())
                    .append("&&token=")
                    .append(token)
                    .toString();
            emailSender.sendEmail(userEmail, resetLink);
            return CommonResponseDTO.createSuccesResponse("Email has been sent");
        } else {
            logger.error("User not found: userId: {}", userEmail);
            return CommonResponseDTO.createFailResponse(new CommonError("404", "User not found with email: "+ userEmail));
        }
    }

    public void updatePasswordForUser(final ResetPasswordRequestDTO resetPasswordRequest) {
//        Optional<UserToken> token = userTokenRepo.findByUserIdAndToken(resetPasswordRequest.getUserId(), resetPasswordRequest.getToken());
        Optional<User> user = userRepository.findById(resetPasswordRequest.userId());
        AccesToken resetToken = user.get().getAccesToken();
        final PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user.isPresent()) {
            if (resetToken != null && resetToken.isActive()) {
                user.get().setPassword(encoder.encode(resetPasswordRequest.newPassword()));
                user.get().getAccesToken().setIsUsed(true);
                userRepository.save(user.get());
            } else {
                logger.error("Token not found or already used :( ");
            }
        } else {
            logger.error("User not found with userId: {}", resetPasswordRequest.userId());
        }
    }
//    public Optional<UserAuthToken> getAuthTokensForUser(final String userId) {
//        return authTokenRepo.findByUserId(userId);
//    }
}
