package com.sonpoll.oradea.sonpoll.common.request;

import lombok.Getter;

@Getter
public record ResetPasswordRequestDTO(
        String userId,
        String token,
        String newPassword) {
}
