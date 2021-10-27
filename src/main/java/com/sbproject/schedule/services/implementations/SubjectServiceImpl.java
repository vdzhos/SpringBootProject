package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.SpecialtyRepository;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.utils.Markers;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    private Utils processor;

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }

    //TO DO - need to add better check
    @Override
    public boolean addSubject(String name, int quantOfGroups, Set<Specialty> specialties) {
        //must not use existsByNameAndSpecialties
//        if(subjectRepository.existsByNameAndSpecialties(name, specialties))
//            return false;
//        subjectRepository.save(new Subject(processor.getUniqueId(), processor.processName(name), quantOfGroups, teachers, specialties));
//        return true;
        if (subjectRepository.existsByName(name) || specialties.isEmpty())
            return false;
        subjectRepository.save(new Subject(name, quantOfGroups, specialties));
        return true;
    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Transactional
    @Override
    public void deleteSubject(Long id) throws Exception{
        if(!subjectExistsById(id)) throw new Exception("Subject with id '"+ id +"' has not been found!");
        subjectRepository.deleteById(id);
    }

    @Override
    public boolean updateSubject(Long id, String name, int quantOfGroups, Set<Teacher> teachers, Set<Specialty> specialties) {
        //subjectRepository.save(new Subject(processor.getUniqueId(), processor.processName(name), quantOfGroups, teachers, specialties));
        //return false;
        Optional<Subject> subjectOp = subjectRepository.findById(id);//.orElseThrow();
        if (subjectOp.isPresent()){
            Subject subject = subjectOp.get();
            //logger.info();
            //if(nothingChanged(subject)) return;
            subject.setName(name);
            subject.setQuantOfGroups(quantOfGroups);
            subject.setTeachers(teachers);
            subject.setSpecialties(specialties);
            subjectRepository.save(subject);
            return true;
        }
        return false;
    }

    @Override
    public Subject updateSubject(Subject subject) throws Exception {
        if(!subjectExistsById(subject.getId())) throw new Exception("Subject with id '"+ subject.getId() +"' has not been found!");
        return subjectRepository.save(subject);
    }

    @Override
    public Iterable<Subject> getAll() {
        return subjectRepository.findAll();
    }


    @Transactional
    public String countTeachers(long subjectId){
        return subjectRepository.findById(subjectId).get().getTeachers().toString();
    }

    @Override
    public Subject getSubjectByName(String name) {
        return subjectRepository.findByName(name).iterator().next();
    }

    @Override
    public Subject getSubjectById(Long id) throws Exception{
        return subjectRepository.findById(id).orElseThrow(() -> new Exception("Subject with id '"+ id +"' has not been found!"));
    }

    @Override
    public boolean subjectExistsById(Long id) {
        return subjectRepository.existsById(id);
    }



}
