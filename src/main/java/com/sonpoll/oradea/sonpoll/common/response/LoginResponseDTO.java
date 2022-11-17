package com.sonpoll.oradea.sonpoll.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String id;
    private String username;
    private String email;
    private String token;
    private final String type = "Bearer";
}
