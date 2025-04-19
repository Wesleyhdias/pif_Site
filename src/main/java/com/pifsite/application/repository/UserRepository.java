package com.pifsite.application.repository;

import com.pifsite.application.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
}
