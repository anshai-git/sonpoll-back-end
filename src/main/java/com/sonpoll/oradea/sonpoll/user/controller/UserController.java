package com.sonpoll.oradea.sonpoll.user.controller;

import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("users")
public class UserController {

    UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }
}
