package com.sonpoll.oradea.sonpoll.auth.controller;

import com.sonpoll.oradea.sonpoll.auth.security.jwt.JwtUtil;
import com.sonpoll.oradea.sonpoll.auth.security.services.UserDetailsImpl;
import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.environment.EnvironmentTask;
import com.sonpoll.oradea.sonpoll.common.environment.EnvironmentUtils;
import com.sonpoll.oradea.sonpoll.common.request.LoginRequestDTO;
import com.sonpoll.oradea.sonpoll.common.request.LogoutRequest;
import com.sonpoll.oradea.sonpoll.common.request.RegisterRequestDTO;
import com.sonpoll.oradea.sonpoll.common.response.LoginResponseDTO;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Value("${environment}")
    private String environment;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    UserServiceImpl userService;

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
                    .body(CommonResponseDTO.createFailResponse(new CommonError("Username already exist")));
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(CommonResponseDTO.createFailResponse(new CommonError("Email already exist")));
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(getPassword(request.getPassword()))
                .build();
        userService.saveUser(user);
        return ResponseEntity.ok(CommonResponseDTO.createSuccesResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO<LoginResponseDTO>> login(@RequestBody final CommonRequestDTO<LoginRequestDTO> loginRequest) {
        final LoginRequestDTO credentials = loginRequest.getPayload();
        final Authentication inputAuthentication = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        final Authentication authentication = authenticationProvider.authenticate(inputAuthentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        /**
         * TODO
         *
         Optional<UserAuthToken> authTokens = userService.getAuthTokensForUser(userDetails.getId());
         if(authTokens.isPresent()){
         if(authTokens.get().getTokens().isEmpty()) {
         Set<String> userTokens = new HashSet<>();
         userTokens.add(token);
         authTokens.get().setTokens(userTokens);
         authTokenRepo.save(authTokens.get());
         } else {
         authTokens.get().getTokens().add(token);
         authTokenRepo.save(authTokens.get());
         }
         }
         *
         * user_auth_tokens
         * "userID": "userida21313",
         * "tokens": [ "token1", "token2" ]
         * login => check if it's first login for user and add new token
         * logout => remove token from list
         */
        return ResponseEntity.ok(
                CommonResponseDTO.createSuccesResponse(LoginResponseDTO.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .token(token)
                        .build()));
    }

    @PostMapping("logout")
    public void logout(@RequestBody CommonRequestDTO<LogoutRequest> logoutRequest) {
        // TODO: 22.01.2023 1 token or multiples ??
//        Optional<UserAuthToken> authTokens = userService.getAuthTokensForUser(logoutRequest.getPayload().userId());
//        if (authTokens.isPresent()) {
//            authTokens.get().getTokens().removeIf(s -> s.equals(logoutRequest.getPayload().token()));
//            authTokenRepo.save(authTokens.get());
//        }
    }

    private String getPassword(final String plainPassword) {
        final EnvironmentTask task = EnvironmentTask.builder()
                .passOnDev( () -> { return plainPassword; })
                .passOnProd( () -> { return encoder.encode(plainPassword); })
                .build();
        try {
            return EnvironmentUtils.handlePasswordByEnv(environment, task);
        } catch (final Exception exception) {
            log.error("Error while trying to get password", exception);
        }
        return null;
    }
}
