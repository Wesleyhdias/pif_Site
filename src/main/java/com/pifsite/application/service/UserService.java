package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.UserRepository;
import com.pifsite.application.dto.CreateUserDTO;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.User;
import com.pifsite.application.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers(){

        List<UserDTO> users = this.userRepository.getAllUsers();

        if(users.isEmpty()){
            throw new RuntimeException("there is no users in the database"); // melhorar depois
        }

        return users;
    }

    public void createUser(CreateUserDTO registerUserDTO){

        Optional<User> user = this.userRepository.findByEmail(registerUserDTO.email());

        if(user.isPresent()){
            throw new RuntimeException("User already exists"); // melhorar depois
        }

        User newUser = new User();
        newUser.setEmail(registerUserDTO.email());
        newUser.setName(registerUserDTO.name());
        newUser.setRole(UserRoles.fromString(registerUserDTO.role()));
        newUser.setPassword(passwordEncoder.encode(registerUserDTO.password()));

        this.userRepository.save(newUser);
    }

    public void deleteOneUser(UUID userId){

        Optional<User> oPuser = this.userRepository.findById(userId);

        if(!oPuser.isPresent()){
            throw new RuntimeException("User don't exists"); // melhorar depois
        }

        User user = oPuser.get();

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User reqUser = (User)userData.getPrincipal();

        if(reqUser.getRole() != UserRoles.ADMIN || user.equals(reqUser)){
            throw new RuntimeException("you can't delete this user");
        }

        this.userRepository.deleteById(userId);
    }
}
