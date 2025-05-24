package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.AttendanceRepository;
import com.pifsite.application.repository.ClassroomRepository;
import com.pifsite.application.repository.StudentRepository;
import com.pifsite.application.dto.CreateAttendanceDTO;
import com.pifsite.application.entities.Attendance;
import com.pifsite.application.entities.Classroom;
import com.pifsite.application.entities.Student;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.User;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository AttendanceRepository;

    public List<Attendance> getAllAttendances(){

        List<Attendance> Attendances = this.AttendanceRepository.findAll();

        if(Attendances.isEmpty()){
            throw new RuntimeException("there is no Attendances in the database"); // melhorar depois
        }

        return Attendances;
    }

    public void crateAttendance(CreateAttendanceDTO AttendanceDTO){

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Student newStudent = this.studentRepository.findById(AttendanceDTO.studentId()).get();
            Classroom newClassroom = this.classroomRepository.findById(AttendanceDTO.classroomId()).get();

            Attendance newAttendance = new Attendance();

            newAttendance.setAttendanceDate(AttendanceDTO.attendanceDate());
            newAttendance.setPresence(AttendanceDTO.presence());
            newAttendance.setStudent(newStudent);
            newAttendance.setClassroom(newClassroom);

            this.AttendanceRepository.save(newAttendance);
        }
    }

    public void deleteOneAttendance(UUID AttendanceId){
        Optional<Attendance> oPAttendance = this.AttendanceRepository.findById(AttendanceId);
        
        if(!oPAttendance.isPresent()){
            throw new RuntimeException("Attendance don't exists"); // melhorar depois
        }

        
        // Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User)userData.getPrincipal();

        this.AttendanceRepository.deleteById(AttendanceId);
    }
}
