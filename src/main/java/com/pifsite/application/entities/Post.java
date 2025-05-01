package com.pifsite.application.entities;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID postId;

    private String title;
    private String body;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    
    @ManyToOne
    @JoinColumn(name = "owner")  // FK da tabela User
    private User owner;

}