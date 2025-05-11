package com.pifsite.application.entities;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "course", nullable = false)
    private Course course;
}
