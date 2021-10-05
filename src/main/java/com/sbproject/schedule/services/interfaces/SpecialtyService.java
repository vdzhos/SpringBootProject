package com.sbproject.schedule.services.interfaces;

import com.demo.customstarter.exceptions.InvalidSpecialtyNameException;
import com.demo.customstarter.exceptions.SpecialtyInstanceAlreadyExistsException;
import com.sbproject.schedule.models.Specialty;

public interface SpecialtyService {

    void addSpecialty(String name, int year) throws SpecialtyInstanceAlreadyExistsException, InvalidSpecialtyNameException;
    void deleteSpecialty(Long id);
//    boolean deleteSpecialty(String name, int year);
    void updateSpecialty(long id, String name, int year) throws InvalidSpecialtyNameException, SpecialtyInstanceAlreadyExistsException;
    Iterable<Specialty> getAll();

}
