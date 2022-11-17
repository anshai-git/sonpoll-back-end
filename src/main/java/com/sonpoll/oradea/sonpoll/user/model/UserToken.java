package com.sonpoll.oradea.sonpoll.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document("user_token")
@Data
@NoArgsConstructor
public class UserToken {
    @Id
    private String id;
    private String userId;
    private String token;
    private Boolean isUsed;
    private LocalDateTime expirationDate;

    public boolean isActive() {
        return !isUsed && LocalDateTime.now().isBefore(expirationDate);
    }

    public UserToken(String userId, String token, Boolean isUsed, LocalDateTime expirationDate) {
        this.userId = userId;
        this.token = token;
        this.isUsed = isUsed;
        this.expirationDate = expirationDate;
    }
}
