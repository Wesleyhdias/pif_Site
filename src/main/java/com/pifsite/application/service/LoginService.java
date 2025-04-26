package com.pifsite.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.pifsite.application.repository.UserRepository;
import com.pifsite.application.dto.LoginDTO;
import com.pifsite.application.entities.User;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String doLogin(LoginDTO loginDTO){
        
        User user = this.userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new RuntimeException("User Not Found"));

        if(passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);

            return token;
        }

        throw new RuntimeException("login failed");
    }

}
