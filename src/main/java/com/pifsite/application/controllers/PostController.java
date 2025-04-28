package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.pifsite.application.service.PostService;
import com.pifsite.application.dto.PostDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO){

        try{

            postService.cratePost(postDTO);
            return ResponseEntity.ok("Post criado");
         
        }catch(Exception err){
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post creation failed, you (Probably) don't have access to post creation feature");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        
        try{

            postService.deleteOnePost(id);
            return ResponseEntity.ok("Post successfully deleted.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not deleted");
        }
    }
}
