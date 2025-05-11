package com.pifsite.application.service;

import com.pifsite.application.repository.CourseRepository;
import com.pifsite.application.repository.StudentRepository;
// import com.pifsite.application.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.pifsite.application.dto.CreateStudentDTO;
import com.pifsite.application.dto.CreateUserDTO;
import com.pifsite.application.entities.Course;
import com.pifsite.application.entities.Student;
// import com.pifsite.application.entities.User;
import com.pifsite.application.enums.UserRoles;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    // private final UserRepository userRepository;
    // private final UserService userService;

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

        Student student = new Student();

        if(!course.isPresent()){
            throw new RuntimeException("Tem esse curso não meu rapaz...");
        }

        Course aCourse = course.get();

        System.out.println(aCourse.getCourseId());

        student.setName(registerUser.name());
        student.setEmail(registerUser.email());
        student.setPassword(registerUser.password());
        student.setRole(UserRoles.fromString(registerUser.role()));
        student.setCourse(aCourse);
        
        studentRepository.save(student);
    }
}
