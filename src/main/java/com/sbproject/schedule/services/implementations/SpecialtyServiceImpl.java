package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.database.Database;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.repositories.fakes.interfaces.SpecialtyRepository;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private SpecialtyRepository specialtyRepository;
    private Utils processor;

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }

    @Autowired
    public void setSpecialtyRepository(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public boolean addSpecialty(String name, int year) {
        if(specialtyRepository.existsByNameAndYear(name, year))
            return false;
        specialtyRepository.save(new Specialty(Database.getUniqueId(), processor.processName(name),year));
        return true;
    }

    @Override
    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }


    @Override
    public boolean updateSpecialty(String name, int year) {
        specialtyRepository.save(new Specialty(Database.getUniqueId(), processor.processName(name),year));
        return false;
    }

    @Override
    public Iterable<Specialty> getAll() {
        return specialtyRepository.findAll();
    }
}
