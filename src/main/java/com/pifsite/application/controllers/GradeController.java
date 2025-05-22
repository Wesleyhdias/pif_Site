package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.pifsite.application.service.GradeService;
import com.pifsite.application.dto.CreateGradeDTO;
import com.pifsite.application.entities.Grade;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseEntity<?> getAllGrades(){

        try{

            List<Grade> grades = gradeService.getAllGrades();
            return ResponseEntity.ok(grades); // não está muito bom ainda tem que arrumar dps

        }catch(Exception err){

            System.out.println(err);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createGrade(@RequestBody CreateGradeDTO gradeDTO){

        try{

            gradeService.crateGrade(gradeDTO);
            return ResponseEntity.ok("Grade created");
         
        }catch(Exception err){
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Grade creation failed, you (Probably) don't have access to Grade creation feature");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable UUID id) {
        
        try{

            gradeService.deleteOneGrade(id);
            return ResponseEntity.ok("Grade successfully deleted.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Grade not deleted");
        }
    }
}
