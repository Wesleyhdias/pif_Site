package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        
        try{

            userService.deleteOneUser(id);
            return ResponseEntity.ok("User successfully deleted.");

        }catch(Exception err) {
            System.out.println(err);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not deleted");
        }
    }
}
