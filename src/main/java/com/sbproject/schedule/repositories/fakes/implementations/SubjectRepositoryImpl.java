package com.sbproject.schedule.repositories.fakes.implementations;

import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.repositories.fakes.interfaces.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubjectRepositoryImpl implements SubjectRepository {

    private Map<Long, Subject> subjectMap;

    @Autowired
    public SubjectRepositoryImpl(Map<Long, Subject> subjectMap) {
        this.subjectMap = subjectMap;
    }

    @Override
    public Iterable<Subject> findAll() {
        return subjectMap.values();
    }

    @Override
    public Subject findById(Long id) {
        return subjectMap.get(id);
    }

    @Override
    public Iterable<Subject> findByName(String name) {
        return subjectMap.values();
    }

    @Override
    public Subject save(Subject s) {
        subjectMap.put(s.getId(),s);
        return s;
    }

    @Override
    public boolean existsByNameAndQuantOfGroups(String name, int year) {
        return false;
    }

    @Override
    public void deleteById(Long id) {
        subjectMap.remove(id);
    }
}
