package com.sonpoll.oradea.sonpoll.common.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
}
