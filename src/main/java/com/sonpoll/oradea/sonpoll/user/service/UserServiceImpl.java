package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.environment.EnvironmentTask;
import com.sonpoll.oradea.sonpoll.common.environment.EnvironmentUtils;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.mail.EmailService;
import com.sonpoll.oradea.sonpoll.user.model.AccesToken;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String RESET_PASS_BASE_PATH = "localhost:4200/auth/resetPasswrod";

    @Value("${environment}")
    private String environment;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailSender;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public CommonResponseDTO sendResetPassEmail(final String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        String resetLink, token;
        if (user.isPresent()) {
            // Optional<UserToken> accesToken =
            // userTokenRepo.findByUserId(user.get().getId());
            AccesToken accesToken = user.get().getAccesToken();
            if (accesToken != null && accesToken.isActive()) {
                token = accesToken.getToken();
                // userTokenRepo.save(accesToken.get());
            } else {
                LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES));
                // UserToken newToken = new UserToken(user.get().getId(),
                // UUID.randomUUID().toString(), false, expirationDate);
                AccesToken newToken = new AccesToken(UUID.randomUUID().toString(), false, expirationDate);
                user.get().setAccesToken(newToken);
                userRepository.save(user.get());
                token = newToken.getToken();
            }
            resetLink = new StringBuilder()
                    .append(RESET_PASS_BASE_PATH)
                    .append("?userId=" .concat(user.get().getId()))
                    .append("&&token=" .concat(token))
                    .toString();

            sendResetMail(userEmail, resetLink);

            return CommonResponseDTO.createSuccesResponse("Email has been sent");
        } else {
            logger.error("User not found: userId: {}", userEmail);
            return CommonResponseDTO
                    .createFailResponse(new CommonError("404", "User not found with email: " + userEmail));
        }
    }

    public void updatePasswordForUser(final ResetPasswordRequestDTO resetPasswordRequest) {
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

    /**
     * NOTE: Depending on the environment it will either just log the reset
     * information or send the email.
     */
    private void sendResetMail(final String emailAddress, final String resetLink) {
        final EnvironmentTask task = EnvironmentTask.builder()
                .onDevServer(() -> {
                    log.info(resetLink);
                    return null;
                })
                .onProdServer(() -> {
                    emailSender.sendEmail(emailAddress, resetLink);
                    return null;
                })
                .build();

        try {
            EnvironmentUtils.handleTask(environment, task);
        } catch (final Exception exception) {
            log.error("Error while trying to send reset password email", exception);
        }
    }
}
