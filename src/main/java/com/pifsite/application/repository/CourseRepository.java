package com.pifsite.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pifsite.application.entities.Course;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

}
