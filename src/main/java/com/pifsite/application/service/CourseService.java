package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.SubjectRepository;
import com.pifsite.application.repository.CourseRepository;
import com.pifsite.application.dto.CourseSubjectsDTO;
import com.pifsite.application.entities.Subject;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.Course;
import com.pifsite.application.entities.User;
import com.pifsite.application.dto.CourseDTO;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    public List<Course> getAllCourses(){

        List<Course> courses = this.courseRepository.findAll();

        if(courses.isEmpty()){
            throw new RuntimeException("there is no posts in the database"); // melhorar depois
        }

        return courses;
    }

    public Course crateCourse(CourseDTO courseDTO){

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Course newCourse = new Course();
            newCourse.setCourse_name(courseDTO.course_name());

            return this.courseRepository.save(newCourse);
        }
        return null;
    }

    public void addSubjectToCourse(UUID courseID, List<UUID> subjectIds){

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Optional<Course> OpCourse = this.courseRepository.findById(courseID);
            
            if(!OpCourse.isPresent()){
                throw new RuntimeException("Esse curso existe não");
            }
            
            System.out.println("até aqui deu certo");

            Course course = OpCourse.get();

            List<Subject> subjects = subjectRepository.findAllById(subjectIds);

            course.getSubjects().addAll(subjects);


            courseRepository.save(course);
        }
    }

    public void createCourseWithSubjects(CourseSubjectsDTO courseSubjectsDTO){

        CourseDTO newCourseDTO = new CourseDTO(courseSubjectsDTO.course_name());
        UUID courseId = crateCourse(newCourseDTO).getCourseId();

        addSubjectToCourse(courseId, courseSubjectsDTO.subjects());
    }

    public void deleteOneCourse(UUID courseId){
        Optional<Course> opCourse = this.courseRepository.findById(courseId);
        
        if(!opCourse.isPresent()){
            throw new RuntimeException("course don't exists"); // melhorar depois
        }

        this.courseRepository.deleteById(courseId);
    }
}
