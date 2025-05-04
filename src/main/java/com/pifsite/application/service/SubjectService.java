package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.SubjectRepository;
import com.pifsite.application.entities.Subject;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.entities.User;
import com.pifsite.application.dto.SubjectDTO;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects(){

        List<Subject> subjects = this.subjectRepository.findAll();

        if(subjects.isEmpty()){
            throw new RuntimeException("there is no posts in the database"); // melhorar depois
        }

        return subjects;
    }

    public void crateSubject(SubjectDTO subjectDTO){

        System.out.println();
        System.out.println();
        System.out.println("entrou aqui pelo menos");
        System.out.println();
        System.out.println();

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Subject newSubject = new Subject();
            newSubject.setSubject_name(subjectDTO.subject_name());
            newSubject.setWorkload_hours(subjectDTO.workload_hours());

            this.subjectRepository.save(newSubject);
        }
    }

    public void deleteOneSubject(UUID subjectId){
        Optional<Subject> opSubject = this.subjectRepository.findById(subjectId);
        
        if(!opSubject.isPresent()){
            throw new RuntimeException("subject don't exists"); // melhorar depois
        }

        this.subjectRepository.deleteById(subjectId);
    }
}
