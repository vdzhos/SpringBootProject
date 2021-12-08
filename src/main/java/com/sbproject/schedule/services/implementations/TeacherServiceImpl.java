package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.teacher.NoTeacherWithSuchIdException;
import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.TeacherRepository;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    private static Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Autowired
    private Utils processor;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects", "allTeachers"}, allEntries = true)
    //TO DO - need to add name check probably
    @Override
    public Teacher addTeacher(String name, Set<Subject> subjects) {
        name = processor.processName(name);
        processor.checkTeacherName(name);
//        processor.checkTeachersSubjects(subjects);
        return teacherRepository.save(new Teacher(name, subjects));
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects", "allTeachers"}, allEntries = true)
    @Override
    public Teacher addTeacher(Teacher teacher) {
        return addTeacher(teacher.getName(), teacher.getSubjects());
    }

    @Override
    public boolean teacherExistsById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public boolean teacherExistsByName(String name) {
        return teacherRepository.existsByName(name);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "allTeachers", allEntries = true),
            @CacheEvict(cacheNames = "teachers", key = "#id")})
    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects","lessons","allLessons"}, allEntries = true)
    @Transactional
    @Override
    public boolean deleteTeacher(Long id) throws NoTeacherWithSuchIdException{
        if(!teacherExistsById(id)) throw new NoTeacherWithSuchIdException(id, "deleted");
        teacherRepository.deleteById(id);
        return true;
    }

    @CachePut(cacheNames = "teachers", key = "#id")
    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects", "allTeachers","lessons","allLessons"}, allEntries = true)
    @Override
    public boolean updateTeacher(Long id, String name) {
        teacherRepository.save(new Teacher(id, name));
        return true;
    }

    @CachePut(cacheNames = "teachers", key = "#teacher.id")
    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects", "allTeachers","lessons","allLessons"}, allEntries = true)
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

    @Cacheable(cacheNames = "teachers", key = "#id")
    @Override
    public Teacher getTeacherById(Long id) throws Exception{
        return teacherRepository.findById(id).orElseThrow(() -> new NoTeacherWithSuchIdException(id, "get"));
    }

   //change teacherRepository.findByName from Iterable<Teacher> to Teacher
//    @Cacheable(cacheNames = "teachers", key = "#name")
//    @Override
//    public Teacher getTeacherByName(String name) throws Exception{
//        return teacherRepository.findByName(name).orElseThrow(() -> new NoTeacherWithSuchIdException(id, "get"));
//    }

    @Override
    public Iterable<Teacher> getTeacherByPartName(String name) throws Exception {
        return teacherRepository.findByPartName(name);
    }

    @Cacheable(cacheNames = "allTeachers")
    @Override
    public Iterable<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Scheduled(fixedDelay = 120000)
    @CacheEvict(cacheNames = "allTeachers", allEntries = true)
    public void clearAllTeachersCache() {
        logger.info(Markers.TEACHER_CACHING_MARKER, "SCHEDULED REMOVAL: All teachers list removed from cache");
    }

    @Scheduled(cron = "0 */2 * ? * *")
    @CacheEvict(cacheNames = "teachers", allEntries = true)
    public void clearTeachersCache() {
        logger.info(Markers.TEACHER_CACHING_MARKER, "SCHEDULED REMOVAL: All specific teachers removed from cache");
    }
}
