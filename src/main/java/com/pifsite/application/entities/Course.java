package com.pifsite.application.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID courseId;

    private String course_name;

    @ManyToMany
    @JoinTable(
        name = "courses_subjects",
        joinColumns = @JoinColumn(name = "fk_course_id"),
        inverseJoinColumns = @JoinColumn(name = "fk_subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();
    
}