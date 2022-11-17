package com.sonpoll.oradea.sonpoll.common.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequestDTO {
    private String userId;
    private String token;
    private String newPassword;
}
