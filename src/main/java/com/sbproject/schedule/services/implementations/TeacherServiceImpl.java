package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    private static Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Autowired
    private Utils utils;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects"}, allEntries = true)
    //TO DO - need to add name check probably
    @Override
    public boolean addTeacher(String name, Set<Subject> subjects) {
        teacherRepository.save(new Teacher(name, subjects));
        return true;
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects"}, allEntries = true)
    @Override
    public Teacher addTeacher(Teacher teacher) {
        teacher.setId(-1L);
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean teacherExistsById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public boolean teacherExistsByName(String name) {
        return teacherRepository.existsByName(name);
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects"}, allEntries = true)
    @Override
    public boolean deleteTeacher(Long id) throws NoTeacherWithSuchIdException{
        if(!teacherExistsById(id)) throw new NoTeacherWithSuchIdException(id, "deleted");
        teacherRepository.deleteById(id);
        return true;
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects"}, allEntries = true)
    @Override
    public boolean updateTeacher(Long id, String name) {
        teacherRepository.save(new Teacher(id, name));
        return true;
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects"}, allEntries = true)
    @Override
    public Teacher updateTeacher(Teacher teacher) throws NoTeacherWithSuchIdException {
        if(!teacherExistsById(teacher.getId())) throw new NoTeacherWithSuchIdException(teacher.getId(), "updated");
        return teacherRepository.save(teacher);
    }


    // need not use @CacheEvict for specialties as the method is invoked in
    // other methods where @CacheEvict for specialties is already specified
    @Override
    public Teacher updateTeacherNoCheck(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Long id) throws Exception{
        return teacherRepository.findById(id).orElseThrow(() -> new NoTeacherWithSuchIdException(id, "get"));
    }

    @Override
    public Iterable<Teacher> getTeacherByPartName(String name) throws Exception {
        return teacherRepository.findByPartName(name);
    }

    @Override
    public Iterable<Teacher> getAll() {
        return teacherRepository.findAll();
    }

}
