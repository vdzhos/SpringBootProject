package com.sbproject.schedule.repositories.fakes.implementations;

import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.fakes.interfaces.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TeacherRepositoryImpl implements TeacherRepository {

    private Map<Long, Teacher> teacherMap;

    @Autowired
    public TeacherRepositoryImpl(Map<Long, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    @Override
    public Iterable<Teacher> findAll() {
        return teacherMap.values();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherMap.get(id);
    }

    //TO DO
    @Override
    public Iterable<Teacher> findByName(String name) {
        return teacherMap.values();
    }

    @Override
    public Teacher save(Teacher t) {
        teacherMap.put(t.getId(),t);
        return t;
    }


    @Override
    public void deleteById(Long id) {
        teacherMap.remove(id);
    }
}
