package me.minseok.member_service.controller;

import me.minseok.member_service.dto.ModifyUserDto;
import me.minseok.member_service.dto.RegisterUserDto;
import me.minseok.member_service.entity.UserEntity;
import me.minseok.member_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    UserService userService;

    @PostMapping("/member/users/registration")
    public UserEntity registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return userService.registerUser(registerUserDto.loginId, registerUserDto.userName);
    }

    @PutMapping("/member/users/{userId}/modify")
    public UserEntity modifyUser(@PathVariable Long userId, @RequestBody ModifyUserDto modifyUserDto) {
        return userService.modifyUser(userId, modifyUserDto.userName);
    }

    @PostMapping("/member/users/{loginId}/login")
    public UserEntity login(@PathVariable String loginId) {
        return userService.getUser(loginId);
    }

}
