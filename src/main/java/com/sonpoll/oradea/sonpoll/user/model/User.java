package com.sonpoll.oradea.sonpoll.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
