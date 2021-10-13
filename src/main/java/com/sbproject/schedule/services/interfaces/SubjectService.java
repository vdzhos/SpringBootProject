package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    boolean addSubject(String name, int quantOfGroups, Set<Specialty> specialties);
    void deleteSubject(Long id);
    boolean updateSubject(String name, int quantOfGroups, Set<Teacher> teachers,  Set<Specialty> specialties);
    Iterable<Subject> getAll();
}
