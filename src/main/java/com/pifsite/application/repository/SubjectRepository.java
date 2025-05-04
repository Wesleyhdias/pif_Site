package com.pifsite.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pifsite.application.entities.Subject;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}
