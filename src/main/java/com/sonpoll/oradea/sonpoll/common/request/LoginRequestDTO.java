package com.sonpoll.oradea.sonpoll.common.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
    private boolean keepLoggedIn;
}
