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

import com.pifsite.application.service.ProfessorService;
import com.pifsite.application.entities.Professor;
import com.pifsite.application.dto.CreateUserDTO;
// import com.pifsite.application.dto.ProfessorDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService ProfessorService;

    @GetMapping
    public ResponseEntity<?> getAllProfessors(){

        try{

            List<Professor> professors = ProfessorService.getAllProfessors();
            return ResponseEntity.ok(professors);

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createProfessor(@RequestBody CreateUserDTO registerProfessorDTO){

        try{

            ProfessorService.createProfessor(registerProfessorDTO);
            return ResponseEntity.ok("Usuário criado");

        }catch(Exception err){

            System.out.println();
            System.out.println("Erro do controller");
            System.out.println(err);
            System.out.println("Erro do controller");
            System.out.println();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor not created");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfessor(@PathVariable UUID id) {
        
        try{

            ProfessorService.deleteOneProfessor(id);
            return ResponseEntity.ok("Professor successfully deleted.");

        }catch(Exception err) {
            
            System.out.println(err);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor not deleted");
        }
    }
}
