package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Teacher;



public interface TeacherService {

    boolean addTeacher(String name);
    void deleteTeacher(Long id);
    boolean updateTeacher(Long id, String name);
    Iterable<Teacher> getAll();
}
