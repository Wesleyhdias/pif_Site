package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.ClassroomRepository;
import com.pifsite.application.repository.ProfessorRepository;
import com.pifsite.application.repository.StudentRepository;
import com.pifsite.application.repository.SubjectRepository;
import com.pifsite.application.dto.CreateClassroomDTO;
import com.pifsite.application.entities.Classroom;
import com.pifsite.application.entities.Professor;
import com.pifsite.application.entities.Student;
import com.pifsite.application.entities.Subject;
import com.pifsite.application.entities.User;
import com.pifsite.application.enums.UserRoles;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    
    private final ClassroomRepository classroomRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public List<Classroom> getAllClassrooms(){ // trocar para retornar um DTO depois

        List<Classroom> Classrooms = this.classroomRepository.findAll();

        if(Classrooms.isEmpty()){
            throw new RuntimeException("there is no Classrooms in the database"); // melhorar depois
        }

        return Classrooms;
    }

    public void createClassroom(CreateClassroomDTO registerClassroomDTO){

        // Optional<Classroom> Classroom = this.classroomRepository.findByEmail(registerClassroomDTO.email());
        Optional<Professor> professor = this.professorRepository.findById(registerClassroomDTO.professorId());
        Optional<Subject> subject = this.subjectRepository.findById(registerClassroomDTO.subjectId());
        
        List<Student> students = this.studentRepository.findAllById(registerClassroomDTO.studentsIds());

        // if(Classroom.isPresent()){
        //     throw new RuntimeException("Classroom already exists"); // melhorar depois
        // }

        Classroom newClassroom = new Classroom();

        newClassroom.setProfessor(professor.get());
        newClassroom.setSubject(subject.get());
        newClassroom.setSemester(registerClassroomDTO.semester());
        newClassroom.setStartAt(registerClassroomDTO.startAt());
        newClassroom.setEndAt(registerClassroomDTO.endAt());

        newClassroom.getStudents().addAll(students);

        this.classroomRepository.save(newClassroom);
    }

    public void deleteOneClassroom(UUID ClassroomId){

        Optional<Classroom> oPClassroom = this.classroomRepository.findById(ClassroomId);

        if(!oPClassroom.isPresent()){
            throw new RuntimeException("Classroom don't exists"); // melhorar depois
        }

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User reqUser = (User)userData.getPrincipal();

        if(reqUser.getRole() != UserRoles.ADMIN){
            throw new RuntimeException("you can't delete this classroom");
        }

        this.classroomRepository.deleteById(ClassroomId);
    }
}
