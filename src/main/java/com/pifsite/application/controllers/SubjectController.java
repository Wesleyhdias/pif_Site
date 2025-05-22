package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.pifsite.application.service.SubjectService;
import com.pifsite.application.dto.CreateSubjectDTO;
import com.pifsite.application.dto.SubjectDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<?> getAllSubjects(){

        try{

            List<SubjectDTO> subject = subjectService.getAllSubjects();
            return ResponseEntity.ok(subject); // não está muito bom ainda tem que arrumar dps

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody CreateSubjectDTO subjectDTO){

        try{

            subjectService.crateSubject(subjectDTO);
            return ResponseEntity.ok("Course created");
         
        }catch(Exception err){
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course creation failed, you (Probably) don't have access to Course creation feature");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        
        try{

            subjectService.deleteOneSubject(id);
            return ResponseEntity.ok("Course successfully deleted.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not deleted");
        }
    }
}
