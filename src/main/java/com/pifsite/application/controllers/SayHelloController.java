package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SayHelloController {
    
    @GetMapping("/sayhello")
    public String sayHello(){

        return "I said 'Hello'";
    }

}
