package com.pifsite.application.dto;

import java.util.UUID;

import com.pifsite.application.entities.Course;

public record StudentDTO(UUID studentId, Course course) {
    
}
