package com.sbproject.schedule.repositories_fakes.implementations;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.repositories_fakes.interfaces.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    private Map<Long, Specialty> specialtyMap;

    @Autowired
    public SpecialtyRepositoryImpl(Map<Long, Specialty> specialtyMap) {
        this.specialtyMap = specialtyMap;
    }

    @Override
    public Iterable<Specialty> findAll() {
        return specialtyMap.values();
    }

    @Override
    public Specialty findById(Long id) {
        return specialtyMap.get(id);
    }

    @Override
    public Iterable<Specialty> findByYear(int year) {
        return specialtyMap.values();
    }

    @Override
    public Iterable<Specialty> findByNameAndYear(String name,int year) {
        return specialtyMap.values();
    }

    @Override
    public Iterable<Specialty> findByName(String name) {
        return specialtyMap.values();
    }

    @Override
    public Specialty save(Specialty s) {
        specialtyMap.put(s.getId(),s);
        System.out.println("Saved specialty: \n"+s);
        return s;
    }

    @Override
    public boolean existsByNameAndYear(String name, int year) {
        return false;
    }

    @Override
    public void deleteById(Long id) {
        specialtyMap.remove(id);
        System.out.println("Specialty with id = "+id +" has been deleted!");
    }
}
