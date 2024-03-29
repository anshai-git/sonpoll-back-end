package com.sonpoll.oradea.sonpoll.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User {
    @MongoId
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    // TODO: 21.01.2023  add access tokens with JsonIgnore
    // @JsonIgnore
    private AccesToken accesToken;

    @Builder
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}