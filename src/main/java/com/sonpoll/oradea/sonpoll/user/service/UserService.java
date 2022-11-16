package com.sonpoll.oradea.sonpoll.user.service;

import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(final String username) {
        return  userRepository.findByUsername(username);
    }
    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}