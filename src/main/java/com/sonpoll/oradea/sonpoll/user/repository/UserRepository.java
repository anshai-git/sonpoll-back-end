package com.sonpoll.oradea.sonpoll.user.repository;

import com.sonpoll.oradea.sonpoll.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
