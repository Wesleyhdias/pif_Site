package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.pifsite.application.dto.CourseSubjectsDTO;
import com.pifsite.application.service.CourseService;
import com.pifsite.application.entities.Course;
import com.pifsite.application.dto.CourseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses(){

        try{

            List<Course> course = courseService.getAllCourses();
            return ResponseEntity.ok(course); // não está muito bom ainda

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO){

        try{

            courseService.crateCourse(courseDTO);
            return ResponseEntity.ok("Course created");
         
        }catch(Exception err){
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course creation failed, you (Probably) don't have access to Course creation feature");
        }
    }

    @PostMapping("/withSubjects")
    public ResponseEntity<?> createCourseWithSubjects(@RequestBody CourseSubjectsDTO courseSubjectsDTO){

        try{

            courseService.createCourseWithSubjects(courseSubjectsDTO);
            return ResponseEntity.ok("Course created");
         
        }catch(Exception err){
            
            System.out.println(err);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course creation failed, you (Probably) don't have access to Course creation feature");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addSubjects(@PathVariable UUID id, @RequestBody List<UUID> subjectIds) {
        
        try{

            courseService.addSubjectToCourse(id, subjectIds);
            return ResponseEntity.ok("Course successfully updated.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not updated, i dunno why...");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        
        try{

            courseService.deleteOneCourse(id);
            return ResponseEntity.ok("Course successfully deleted.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not deleted");
        }
    }
}
