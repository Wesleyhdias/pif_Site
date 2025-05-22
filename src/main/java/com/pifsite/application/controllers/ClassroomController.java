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

import com.pifsite.application.service.ClassroomService;
import com.pifsite.application.dto.CreateClassroomDTO;
import com.pifsite.application.entities.Classroom;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public ResponseEntity<?> getAllClassrooms(){

        try{

            List<Classroom> Classrooms = classroomService.getAllClassrooms();
            return ResponseEntity.ok(Classrooms); // não está muito bom ainda tem que arrumar dps

        }catch(Exception err){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createClassroom(@RequestBody CreateClassroomDTO ClassroomDTO){

        try{

            classroomService.createClassroom(ClassroomDTO);
            return ResponseEntity.ok("Classroom created");
         
        }catch(Exception err){
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom creation failed, you (Probably) don't have access to Classroom creation feature");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        
        try{

            classroomService.deleteOneClassroom(id);
            return ResponseEntity.ok("Classroom successfully deleted.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom not deleted");
        }
    }
}
