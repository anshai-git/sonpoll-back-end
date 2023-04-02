package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.request.RegisterRequestDTO;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    User saveUser(User user);

    CommonResponseDTO sendResetPassEmail(String userEmail);

    void updatePasswordForUser(ResetPasswordRequestDTO resetPasswordRequest);

    CommonResponseDTO registerUser(final RegisterRequestDTO registerRequest);
}
