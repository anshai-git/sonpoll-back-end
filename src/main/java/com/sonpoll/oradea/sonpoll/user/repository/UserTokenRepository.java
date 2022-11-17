package com.sonpoll.oradea.sonpoll.user.repository;

import com.sonpoll.oradea.sonpoll.user.model.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTokenRepository extends MongoRepository<UserToken, String> {
    Optional<UserToken> findByUserId(String userId);
    Optional<UserToken> findByUserIdAndToken(String userId, String token);
}
