package com.pifsite.application.dto;

import java.util.List;
import java.util.UUID;

public record CourseSubjectsDTO(String course_name, List<UUID> subjects) {}
