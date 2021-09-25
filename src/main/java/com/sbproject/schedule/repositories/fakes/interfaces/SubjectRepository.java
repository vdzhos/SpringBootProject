package com.sbproject.schedule.repositories.fakes.interfaces;

import com.sbproject.schedule.models.Subject;

public interface SubjectRepository {

    Iterable<Subject> findAll();
    Subject findById(Long id);
    Iterable<Subject> findByName(String name);

    Subject save(Subject s);
    boolean existsByNameAndQuantOfGroups(String name, int quantOfGroups);
    void deleteById(Long id);
}
