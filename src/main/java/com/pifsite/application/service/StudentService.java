package com.pifsite.application.service;

import com.pifsite.application.repository.StudentRepository;
import com.pifsite.application.repository.CourseRepository;
import com.pifsite.application.repository.UserRepository;
import com.pifsite.application.dto.CreateStudentDTO;
import com.pifsite.application.dto.CreateUserDTO;
import com.pifsite.application.entities.Student;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.Course;
import com.pifsite.application.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<Student> getAllStudents(){

        List<Student> students = this.studentRepository.findAll();

        if(students.isEmpty()){
            throw new RuntimeException("there is no Students in the database"); // melhorar depois
        }

        return students;
    }

    @Transactional
    public void createStudent(CreateStudentDTO registerStudentDTO){

        CreateUserDTO registerUser = registerStudentDTO.registerUser();

        Optional<Course> course = courseRepository.findById(registerStudentDTO.courseId());

        User user = new User( null,
            registerUser.name(),
            registerUser.email(),
            passwordEncoder.encode(registerUser.password()),
            UserRoles.fromString(registerUser.role())
        );

        Student student = new Student();

        if(!course.isPresent()){
            throw new RuntimeException("Tem esse curso não meu rapaz...");
        }

        Course aCourse = course.get();

        student.setUser(user);
        student.setCourse(aCourse);
        
        studentRepository.save(student);
    }

    public void deleteOneStudent(UUID studentId){

        Optional<Student> opStudent = this.studentRepository.findById(studentId);

        if(!opStudent.isPresent()){
            throw new RuntimeException("Student don't exists"); // melhorar depois
        }

        User studentUser = this.userRepository.findById(studentId).get();

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User reqUser = (User)userData.getPrincipal();

        if(reqUser.getRole() != UserRoles.ADMIN || studentUser.equals(reqUser)){
            throw new RuntimeException("you can't delete this user");
        }

        this.studentRepository.deleteById(studentId);
        this.userRepository.deleteById(studentId);
    }
}
