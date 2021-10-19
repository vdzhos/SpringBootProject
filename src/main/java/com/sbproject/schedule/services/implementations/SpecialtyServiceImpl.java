package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.exceptions.specialty.SpecialtyInstanceAlreadyExistsException;
import com.sbproject.schedule.models.Specialty;

import com.sbproject.schedule.repositories.SpecialtyRepository;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.utils.Utils;
import com.sbproject.schedule.utils.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private SpecialtyRepository specialtyRepository;

    @Autowired
    private Utils processor;

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }

    @Autowired
    public void setSpecialtyRepository(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    //@ResponseBody
    //@RequestMapping("/")
    @Override
    public void addSpecialty(String name, int year) throws SpecialtyInstanceAlreadyExistsException, InvalidSpecialtyNameException {
        name = processor.processName(name);
        processor.checkName(name);
        if(specialtyRepository.existsByNameAndYear(name, year))
            throw new SpecialtyInstanceAlreadyExistsException(Values.SPECIALTY_ALREADY_EXISTS);
        specialtyRepository.save(new Specialty(name,year));
    }

    @Transactional
    @Override
    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }


    @Override
    public void updateSpecialty(long id, String name, int year) throws InvalidSpecialtyNameException, SpecialtyInstanceAlreadyExistsException {
        name = processor.processName(name);
        processor.checkName(name);
        if(specialtyRepository.existsByNameAndYearAndId(id,name,year)){
            throw new SpecialtyInstanceAlreadyExistsException(Values.SPECIALTY_ALREADY_EXISTS);
        }
        Optional<Specialty> specialtyOp = specialtyRepository.findById(id);//.orElseThrow();
        if (specialtyOp.isPresent()){
            Specialty specialty = specialtyOp.get();

            if(nothingChanged(specialty,name,year)) return;

            specialty.setName(name);
            specialty.setYear(year);
            specialtyRepository.save(specialty);
        }
    }

    private boolean nothingChanged(Specialty specialty, String name, int year) {
        return specialty.getName().equals(name)&&specialty.getYear()==year;
    }

    @Override
    public Iterable<Specialty> getAll() {
        return specialtyRepository.findAll();
    }

}
