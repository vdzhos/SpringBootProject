package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.List;

public interface SubjectService {

    boolean addSubject(String name, int quantOfGroups, List<Teacher> teachers);
    void deleteSubject(Long id);
    //boolean deleteSubject(String name, int quantOfGroups, List<Teacher> teachers);
    boolean updateSubject(String name, int quantOfGroups, List<Teacher> teachers);
    Iterable<Subject> getAll();
}
