package com.pifsite.application.service;

import com.pifsite.application.repository.PostRepository;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.Post;
import com.pifsite.application.entities.User;
import com.pifsite.application.dto.PostDTO;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void cratePost(PostDTO postDTO){

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Post newPost = new Post();
            newPost.setTitle(postDTO.title());
            newPost.setBody(postDTO.body());
            newPost.setOwner(user);

            this.postRepository.save(newPost);
        }
    }
}
