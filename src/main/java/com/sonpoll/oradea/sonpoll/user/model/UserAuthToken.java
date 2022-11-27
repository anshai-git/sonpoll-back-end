package com.sonpoll.oradea.sonpoll.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

@Document("user_auth_tokens")
@Data
@NoArgsConstructor
public class UserAuthToken {
    @Id
    private String id;
    private String userId;
    private Set<String> tokens;
}
