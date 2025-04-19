package com.pifsite.application.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.pifsite.application.repository.UserRepository;
// import com.pifsite.application.service.TokenService;
import com.pifsite.application.dto.RegisterUserDTO;
import com.pifsite.application.entities.User;
import com.pifsite.application.enums.UserRoles;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    // private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO){

        Optional<User> user = this.userRepository.findByEmail(registerUserDTO.email());

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setEmail(registerUserDTO.email());
            newUser.setName(registerUserDTO.name());
            newUser.setRole(UserRoles.fromString(registerUserDTO.role()));
            newUser.setPassword(passwordEncoder.encode(registerUserDTO.password()));

            this.userRepository.save(newUser);

            return ResponseEntity.ok("Usuário criado");
        }

        
        return ResponseEntity.badRequest().build();

    }
}
