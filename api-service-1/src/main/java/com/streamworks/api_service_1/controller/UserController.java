package com.streamworks.api_service_1.controller;

import com.streamworks.api_service_1.dto.UserCombinedResponse;
import com.streamworks.api_service_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserCombinedResponse> getCombinedUsers() {
        return userService.getCombinedUserData();
    }
}