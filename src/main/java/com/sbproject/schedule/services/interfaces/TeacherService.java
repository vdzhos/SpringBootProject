package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.Set;


public interface TeacherService {

    boolean addTeacher(String name, Set<Subject> subjects);
    void deleteTeacher(Long id);
    boolean updateTeacher(Long id, String name);
    Iterable<Teacher> getAll();
}
