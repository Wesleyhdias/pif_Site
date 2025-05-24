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

import com.pifsite.application.service.AttendanceService;
import com.pifsite.application.dto.CreateAttendanceDTO;
import com.pifsite.application.entities.Attendance;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<?> getAllAttendances(){

        try{

            List<Attendance> attendances = attendanceService.getAllAttendances();
            return ResponseEntity.ok(attendances); // não está muito bom ainda tem que arrumar dps

        }catch(Exception err){

            System.out.println(err);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim..");
        }
    }

    @PostMapping
    public ResponseEntity<?> createAttendance(@RequestBody CreateAttendanceDTO attendanceDTO){

        try{

            attendanceService.crateAttendance(attendanceDTO);
            return ResponseEntity.ok("Attendance created");
         
        }catch(Exception err){
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attendance creation failed, you (Probably) don't have access to Attendance creation feature");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable UUID id) {
        
        try{

            attendanceService.deleteOneAttendance(id);
            return ResponseEntity.ok("Attendance successfully deleted.");

        }catch(Exception err) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attendance not deleted");
        }
    }
}
