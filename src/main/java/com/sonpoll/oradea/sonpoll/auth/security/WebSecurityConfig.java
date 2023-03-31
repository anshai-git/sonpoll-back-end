package com.sonpoll.oradea.sonpoll.auth.security;

import com.sonpoll.oradea.sonpoll.auth.security.jwt.AuthEntryPointJwt;
import com.sonpoll.oradea.sonpoll.auth.security.jwt.AuthTokenFilter;
import com.sonpoll.oradea.sonpoll.common.environment.EnvironmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableMongoRepositories
public class WebSecurityConfig {

    @Value("${environment}")
    private String environment;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return switch (EnvironmentType.fromValue(environment)) {
            case DEV -> NoOpPasswordEncoder.getInstance();
            case PROD -> new BCryptPasswordEncoder();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                // TODO: faker endpoints should have serious authorization on production, maybe it should be completely disabled there
                // make it depend on the "environment"
                .requestMatchers("/faker/**").permitAll()
                .requestMatchers("**").permitAll()
                .requestMatchers("/users/*").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
