package com.sonpoll.oradea.sonpoll.common.environment.profiler;

import com.sonpoll.oradea.sonpoll.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile({"PROD"})
public class ProdService implements EnvironmentTask {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailSender;

    @Bean
    public PasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
    }

    @Override
    public String getPassword(final String plainPassword) {
        return encoder.encode(plainPassword);
    }

    @Override
    public void sendResetPasswordEmail(final String emailAddress, final String resetLink) {
        emailSender.sendEmail(emailAddress, resetLink);
    }
}
