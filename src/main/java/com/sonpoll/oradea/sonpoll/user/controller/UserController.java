package com.sonpoll.oradea.sonpoll.user.controller;

import com.sonpoll.oradea.sonpoll.common.CommonError;
import com.sonpoll.oradea.sonpoll.common.CommonRequestDTO;
import com.sonpoll.oradea.sonpoll.common.CommonResponseDTO;
import com.sonpoll.oradea.sonpoll.common.request.ResetPasswordRequestDTO;
import com.sonpoll.oradea.sonpoll.mail.ResetEmailRequest;
import com.sonpoll.oradea.sonpoll.user.model.User;
import com.sonpoll.oradea.sonpoll.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping("/resetPassword")
    public CommonResponseDTO resetPassword(@RequestBody CommonRequestDTO<ResetEmailRequest> request)  {
       return userService.sendResetPassEmail(request.getPayload().getEmail());
    }

    @PostMapping("/updatePassword")
    public CommonResponseDTO updatePasswordForUser(@RequestBody CommonRequestDTO<ResetPasswordRequestDTO> request) {
        try {
            userService.updatePasswordForUser(request.getPayload());
        } catch (Exception e) {
            return CommonResponseDTO.createFailResponse(new CommonError("Error updating the password"));
        }
        return CommonResponseDTO.createSuccesResponse("User password updated.");
    }
}
