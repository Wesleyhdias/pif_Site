package com.pifsite.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pifsite.application.entities.Post;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {}
