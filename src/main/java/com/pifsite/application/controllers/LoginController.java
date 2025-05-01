package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.pifsite.application.service.LoginService;
import com.pifsite.application.dto.LoginDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){

        try{
            
            String token = loginService.doLogin(loginDTO);
            return ResponseEntity.ok(token);

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login failed");
        }

    }
}
