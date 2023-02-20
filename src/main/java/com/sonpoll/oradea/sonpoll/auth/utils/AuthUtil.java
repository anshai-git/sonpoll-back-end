package com.sonpoll.oradea.sonpoll.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sonpoll.oradea.sonpoll.auth.security.services.UserDetailsImpl;

@Component
public class AuthUtil {

    public Authentication getCurrentAuthentication() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }

    public UserDetailsImpl getCurrendUser() {
        return (UserDetailsImpl) getCurrentAuthentication().getPrincipal();
    }
}
