package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.exceptions.specialty.SpecialtyInstanceAlreadyExistsException;
import com.sbproject.schedule.models.Specialty;

import java.util.List;
import java.util.Set;

public interface SpecialtyService {

    void addSpecialty(String name, int year) throws SpecialtyInstanceAlreadyExistsException, InvalidSpecialtyNameException;
    void deleteSpecialty(Long id);
//    boolean deleteSpecialty(String name, int year);
    void updateSpecialty(long id, String name, int year) throws InvalidSpecialtyNameException, SpecialtyInstanceAlreadyExistsException;
    Iterable<Specialty> getAll();

    Set<Specialty> getSpecialtiesByIds(List<Long> specialtiesIds);
}
