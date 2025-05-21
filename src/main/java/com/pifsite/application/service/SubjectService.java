package com.pifsite.application.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pifsite.application.repository.SubjectRepository;
import com.pifsite.application.dto.CreateSubjectDTO;
import com.pifsite.application.entities.Subject;
import com.pifsite.application.enums.UserRoles;
import com.pifsite.application.dto.SubjectDTO;
import com.pifsite.application.entities.User;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<SubjectDTO> getAllSubjects(){ // trocar para retornar um DTO depois

        List<SubjectDTO> subjects = this.subjectRepository.getAllSubjects();

        if(subjects.isEmpty()){
            throw new RuntimeException("there is no posts in the database"); // melhorar depois
        }

        return subjects;
    }

    public void crateSubject(CreateSubjectDTO subjectDTO){

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)userData.getPrincipal();
        
        if(user.getRole() == UserRoles.ADMIN || user.getRole() == UserRoles.PROFESSOR){

            Subject newSubject = new Subject();
            newSubject.setSubjectName(subjectDTO.subjectName());
            newSubject.setWorkloadHours(subjectDTO.workloadHours());

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
