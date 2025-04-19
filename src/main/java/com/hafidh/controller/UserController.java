package com.hafidh.controller;

import com.hafidh.dto.UserDTO;
import com.hafidh.dto.UserProfileDto;
import com.hafidh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserProfileDto dto) {
        userService.updateUserProfile(id, dto);
    }


}