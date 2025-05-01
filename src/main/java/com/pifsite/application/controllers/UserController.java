package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.pifsite.application.service.UserService;
import com.pifsite.application.dto.CreateUserDTO;
import com.pifsite.application.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){

        try{

            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO registerUserDTO){

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
