package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.ClassroomRepository;
import com.pifsite.application.repository.GradeRepository;
import com.pifsite.application.repository.StudentRepository;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.Grade;
import com.pifsite.application.entities.Classroom;
import com.pifsite.application.entities.Student;
import com.pifsite.application.entities.User;
import com.pifsite.application.dto.CreateGradeDTO;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public List<Grade> getAllGrades(){

        List<Grade> grades = this.gradeRepository.findAll();

        if(grades.isEmpty()){
            throw new RuntimeException("there is no Grades in the database"); // melhorar depois
        }

        return grades;
    }

    public void crateGrade(CreateGradeDTO gradeDTO){

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Student newStudent = this.studentRepository.findById(gradeDTO.studentId()).get();
            Classroom newClassroom = this.classroomRepository.findById(gradeDTO.classroomId()).get();

            Grade newGrade = new Grade();

            newGrade.setActivity(gradeDTO.activity());
            newGrade.setGrade(gradeDTO.grade());
            newGrade.setStudent(newStudent);
            newGrade.setClassroom(newClassroom);

            this.gradeRepository.save(newGrade);
        }
    }

    public void deleteOneGrade(UUID gradeId){
        Optional<Grade> oPGrade = this.gradeRepository.findById(gradeId);
        
        if(!oPGrade.isPresent()){
            throw new RuntimeException("Grade don't exists"); // melhorar depois
        }

        
        // Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User)userData.getPrincipal();

        this.gradeRepository.deleteById(gradeId);
    }
}
