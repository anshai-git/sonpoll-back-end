package com.sonpoll.oradea.sonpoll.user.model.repository;

import com.sonpoll.oradea.sonpoll.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

}
