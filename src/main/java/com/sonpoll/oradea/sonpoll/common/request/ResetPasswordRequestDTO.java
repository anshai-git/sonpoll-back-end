package com.sonpoll.oradea.sonpoll.common.request;

public record ResetPasswordRequestDTO(
        String userId,
        String token,
        String newPassword) {
}
