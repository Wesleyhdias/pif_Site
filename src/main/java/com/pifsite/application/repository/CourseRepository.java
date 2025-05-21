package com.pifsite.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pifsite.application.dto.CourseDTO;
import com.pifsite.application.entities.Course;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query("SELECT new com.pifsite.application.dto.CourseDTO(c.courseId, c.courseName) FROM Course c")
    List<CourseDTO> getAllCourses();
    
}