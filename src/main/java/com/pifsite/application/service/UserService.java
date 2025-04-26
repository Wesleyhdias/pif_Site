package com.pifsite.application.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pifsite.application.dto.RegisterUserDTO;
import com.pifsite.application.entities.User;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createUser(RegisterUserDTO registerUserDTO){

        Optional<User> user = this.userRepository.findByEmail(registerUserDTO.email());

        if(user.isPresent()){
            throw new RuntimeException("User already exists"); // depois pode personalizar isso melhor
        }

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setEmail(registerUserDTO.email());
            newUser.setName(registerUserDTO.name());
            newUser.setRole(UserRoles.fromString(registerUserDTO.role()));
            newUser.setPassword(passwordEncoder.encode(registerUserDTO.password()));

            this.userRepository.save(newUser);
        }
    }
}
