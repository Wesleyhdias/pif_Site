package com.pifsite.application.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import com.pifsite.application.dto.LoginDTO;

import com.pifsite.application.entities.User;
import com.pifsite.application.repository.UserRepository;

@Service
public class LoginService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),  new ArrayList<>());
        
    }
}
