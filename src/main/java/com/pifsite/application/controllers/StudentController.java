package com.pifsite.application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.pifsite.application.service.StudentService;
import com.pifsite.application.dto.CreateStudentDTO;
// import com.pifsite.application.dto.StudentDTO;
import com.pifsite.application.entities.Student;

import lombok.RequiredArgsConstructor;

// import java.util.UUID;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(){

        try{

            List<Student> Students = studentService.getAllStudents();
            return ResponseEntity.ok(Students);

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody CreateStudentDTO registerStudentDTO){

        try{

            studentService.createStudent(registerStudentDTO);
            return ResponseEntity.ok("Usuário criado");

        }catch(Exception err){

            System.out.println();
            System.out.println("Erro do controller");
            System.out.println(err);
            System.out.println("Erro do controller");
            System.out.println();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not created");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        
        try{

            studentService.deleteOneStudent(id);
            return ResponseEntity.ok("Student successfully deleted.");

        }catch(Exception err) {
            
            System.out.println(err);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not deleted");
        }
    }
}
