package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        List<SubjectDTO> subject = subjectService.getAllSubjects();
        return ResponseEntity.ok(subject); // não está muito bom ainda tem que arrumar dps

    }

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody CreateSubjectDTO subjectDTO){

        subjectService.crateSubject(subjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course created");
         
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        subjectService.deleteOneSubject(id);
        return ResponseEntity.ok("Course successfully deleted.");

    }
}
