package com.example.assignment.service;

import com.example.assignment.dto.LoginReq;
import com.example.assignment.dto.RegisterReq;
import com.example.assignment.entity.User;
import com.example.assignment.repository.UserRepository;
import com.example.assignment.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtToken jwtToken;

    public String register(RegisterReq request){
        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully!!!";
    }

    public String login(LoginReq request){

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found!"));

        boolean isPasswordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!isPasswordMatches) throw new RuntimeException("Invalid Password!");

        return jwtToken.generateTokenByEmail(user.getEmail());
    }

}
