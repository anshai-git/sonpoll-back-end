package com.sonpoll.oradea.sonpoll.auth.controller;

import com.sonpoll.oradea.sonpoll.auth.security.jwt.JwtUtil;
import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.request.RegisterRequestDTO;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CommonRequestDTO<RegisterRequestDTO> registerRequest) {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username(registerRequest.getPayload().getUsername())
                .email(registerRequest.getPayload().getEmail())
                .password(registerRequest.getPayload().getPassword())
                .build();
        if (userService.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(CommonResponseDTO.createFailResponse("Username already exist"));
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(CommonResponseDTO.createFailResponse("Email already exist"));
        }
        User user = new User(request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok(CommonResponseDTO.createSuccesResponse(user));
    }
}
