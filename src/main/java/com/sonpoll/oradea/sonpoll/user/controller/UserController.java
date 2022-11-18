package com.sonpoll.oradea.sonpoll.user.controller;

import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.mail.ResetEmailRequest;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody CommonRequestDTO<ResetEmailRequest> request) throws MessagingException, IOException {
        userService.sendResetPassEmail(request.getPayload().getEmail());
        return "Email sent";
    }

    @PostMapping("/updatePassword")
    public String updatePasswordForUser(@RequestBody CommonRequestDTO<ResetPasswordRequestDTO> request) throws MessagingException, IOException {
        userService.updatePasswordForUser(request.getPayload());
        return "Pass reseted";
    }

}
