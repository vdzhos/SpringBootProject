package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    Subject addSubject(String name, int quantOfGroups, Set<Specialty> specialties);
    Subject addSubject(Subject subject);
    void deleteSubject(Long id);
    //Subject updateSubject(Long id, String name, int quantOfGroups, Set<Teacher> teachers,  Set<Specialty> specialties);
    Subject updateSubject(Long id, String name, int quantOfGroups, Set<Specialty> specialties);
    Subject updateSubject(Subject subject);
    Subject updateSubjectNoCheck(Subject subject);
    Iterable<Subject> getAll();
    Subject getSubjectByName(String name);
    Subject getSubjectById(Long id);
    boolean subjectExistsById(Long id);
    boolean subjectExistsByName(String name);
    
    Set<Integer> getLessonWeeks(Long id);
    
    Set<Integer> getLessonWeeks(Set<Long> ids);
    
}
