package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pifsite.application.service.UserService;
import com.pifsite.application.dto.RegisterUserDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody RegisterUserDTO registerUserDTO){

        try{

            userService.createUser(registerUserDTO);
            return ResponseEntity.ok("Usuário criado");

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        }
    }
}
