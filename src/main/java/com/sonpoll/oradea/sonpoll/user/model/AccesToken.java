package com.sonpoll.oradea.sonpoll.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccesToken {
    private String token;
    private Boolean isUsed;
    private LocalDateTime expirationDate;

    public boolean isActive() {
        return !isUsed && LocalDateTime.now().isBefore(expirationDate);
    }

}
