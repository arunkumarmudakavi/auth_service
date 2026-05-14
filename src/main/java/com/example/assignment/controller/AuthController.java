package com.example.assignment.controller;

import com.example.assignment.dto.LoginReq;
import com.example.assignment.dto.RegisterReq;
import com.example.assignment.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterReq request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginReq request) {
        return authService.login(request);
    }
}
