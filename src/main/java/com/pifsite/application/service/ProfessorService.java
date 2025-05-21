package com.pifsite.application.service;

import com.pifsite.application.repository.ProfessorRepository;
import com.pifsite.application.repository.UserRepository;
import com.pifsite.application.entities.Professor;
import com.pifsite.application.dto.CreateUserDTO;
import com.pifsite.application.enums.UserRoles;
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
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<Professor> getAllProfessors(){ // trocar para retornar um DTO depois

        List<Professor> Professors = this.professorRepository.findAll();

        if(Professors.isEmpty()){
            throw new RuntimeException("there is no Professors in the database"); // melhorar depois
        }

        return Professors;
    }

    @Transactional
    public void createProfessor(CreateUserDTO registerProfessorDTO){


        User user = new User( null,
            registerProfessorDTO.name(),
            registerProfessorDTO.email(),
            passwordEncoder.encode(registerProfessorDTO.password()),
            UserRoles.fromString(registerProfessorDTO.role())
        );

        Professor Professor = new Professor();

        Professor.setUser(user);
        
        professorRepository.save(Professor);
    }

    public void deleteOneProfessor(UUID professorId){

        Optional<Professor> opProfessor = this.professorRepository.findById(professorId);

        if(!opProfessor.isPresent()){
            throw new RuntimeException("Professor don't exists"); // melhorar depois
        }

        User ProfessorUser = this.userRepository.findById(professorId).get();

        Authentication userData = SecurityContextHolder.getContext().getAuthentication();
        User reqUser = (User)userData.getPrincipal();

        if(reqUser.getRole() != UserRoles.ADMIN || ProfessorUser.equals(reqUser)){
            throw new RuntimeException("you can't delete this user");
        }

        this.professorRepository.deleteById(professorId);
        this.userRepository.deleteById(professorId);
    }
}
