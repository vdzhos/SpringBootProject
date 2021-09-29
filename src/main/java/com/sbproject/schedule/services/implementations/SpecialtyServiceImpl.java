package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.database.Database;
import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.exceptions.specialty.SpecialtyInstanceAlreadyExistsException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.repositories.fakes.interfaces.SpecialtyRepository;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.utils.Utils;
import com.sbproject.schedule.utils.Values;
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
    public void addSpecialty(String name, int year) throws SpecialtyInstanceAlreadyExistsException, InvalidSpecialtyNameException {
        name = processor.processName(name);
        processor.checkName(name);
        if(specialtyRepository.existsByNameAndYear(name, year))
            throw new SpecialtyInstanceAlreadyExistsException(Values.SPECIALTY_ALREADY_EXISTS);
        specialtyRepository.save(new Specialty(Database.getUniqueId(), name,year));
    }

    @Override
    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }


    @Override
    public void updateSpecialty(long id, String name, int year) throws InvalidSpecialtyNameException, SpecialtyInstanceAlreadyExistsException {
        name = processor.processName(name);
        processor.checkName(name);
        if(specialtyRepository.existsByNameAndYear(name,year)){
            throw new SpecialtyInstanceAlreadyExistsException(Values.SPECIALTY_ALREADY_EXISTS);
        }
        Specialty specialty = specialtyRepository.findById(id);//.orElseThrow();
        specialty.setName(name);
        specialty.setYear(year);
        specialtyRepository.save(specialty);
    }

    @Override
    public Iterable<Specialty> getAll() {
        return specialtyRepository.findAll();
    }
}
