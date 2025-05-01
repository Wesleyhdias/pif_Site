package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.PostRepository;
import com.pifsite.application.dto.CreatePostDTO;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.Post;
import com.pifsite.application.entities.User;
import com.pifsite.application.dto.PostDTO;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDTO> getAllPosts(){

        List<PostDTO> posts = this.postRepository.getAllPosts();

        if(posts.isEmpty()){
            throw new RuntimeException("there is no posts in the database"); // melhorar depois
        }

        return posts;
    }

    public void cratePost(CreatePostDTO postDTO){

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

    public void deleteOnePost(UUID postId){
        Optional<Post> oPpost = this.postRepository.findById(postId);
        
        if(!oPpost.isPresent()){
            throw new RuntimeException("post don't exists"); // melhorar depois
        }

        Post post = oPpost.orElse(null); 
        
        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();

        if(!post.getOwner().equals(user)){

            throw new RuntimeException("you can't delete this post"); // melhorar depois
        }

        this.postRepository.deleteById(postId);
    }
}
