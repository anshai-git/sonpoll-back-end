package com.sonpoll.oradea.sonpoll.user.repository;

import com.sonpoll.oradea.sonpoll.user.model.UserAuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthTokenRepo extends MongoRepository<UserAuthToken, String> {
    Optional<UserAuthToken> findByUserId(String userId);
}
