package com.pifsite.application.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pifsite.application.repository.UserRepository;
import com.pifsite.application.service.TokenService;
import com.pifsite.application.dto.LoginDTO;
import com.pifsite.application.entities.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){

        User user = this.userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new RuntimeException("User Not Found"));

        if(passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);

            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login failed");

    }
}
