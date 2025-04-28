package com.pifsite.application.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.UserRepository;
import com.pifsite.application.dto.RegisterUserDTO;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.User;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createUser(RegisterUserDTO registerUserDTO){

        Optional<User> user = this.userRepository.findByEmail(registerUserDTO.email());

        if(user.isPresent()){
            throw new RuntimeException("User already exists"); // melhorar depois
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

    public void deleteOneUser(UUID userId){

        Optional<User> oPuser = this.userRepository.findById(userId);

        if(!oPuser.isPresent()){
            throw new RuntimeException("User don't exists"); // melhorar depois
        }

        User user = oPuser.orElse(null);

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User reqUser = (User)userData.getPrincipal();

        if(reqUser.getRole() != UserRoles.ADMIN || user.equals(reqUser)){
            System.out.println(user.getRole() + " " + UserRoles.ADMIN);
            System.out.println(user.equals(reqUser));
            throw new RuntimeException("you can't delete this user");
        }

        this.userRepository.deleteById(userId);
    }
}
