package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.environment.profiler.EnvironmentTask;
import com.sonpoll.oradea.sonpoll.common.request.RegisterRequestDTO;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.user.model.AccesToken;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnvironmentTask environmentTask;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public CommonResponseDTO registerUser(final RegisterRequestDTO registerRequest) {
        if (findByUsername(registerRequest.getUsername()).isPresent()) {
            return CommonResponseDTO.createFailResponse(new CommonError("Username already exist"));
        }

        if (existsByEmail(registerRequest.getEmail())) {
            return CommonResponseDTO.createFailResponse(new CommonError("Email already exist"));
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(environmentTask.getPassword(registerRequest.getPassword()))
                .build();
        return CommonResponseDTO.createSuccesResponse(saveUser(user));
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public CommonResponseDTO sendResetPassEmail(final String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        String resetLink, token;
        if (user.isPresent()) {
            AccesToken accesToken = user.get().getAccesToken();
            if (accesToken != null && accesToken.isActive()) {
                token = accesToken.getToken();
            } else {
                LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES));
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
            environmentTask.sendResetPasswordEmail(userEmail, resetLink);

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

}
