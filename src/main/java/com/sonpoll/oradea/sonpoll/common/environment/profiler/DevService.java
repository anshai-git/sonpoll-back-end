package com.sonpoll.oradea.sonpoll.common.environment.profiler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile({"default", "DEV"})
public class DevService implements EnvironmentTask {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public String getPassword(final String plainPassword) {
        return plainPassword;
    }

    @Override
    public void sendResetPasswordEmail(final String emailAddress, final String resetLink) {
        log.debug("Reset pass link : ".concat(resetLink));
        log.info("Reset pass link : ".concat(resetLink));
    }
}
