package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.List;

public interface SubjectService {

    boolean addSubject(String name, int quantOfGroups, List<Teacher> teachers,  List<Specialty> specialties);
    void deleteSubject(Long id);
    boolean updateSubject(String name, int quantOfGroups, List<Teacher> teachers,  List<Specialty> specialties);
    Iterable<Subject> getAll();
}
