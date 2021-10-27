package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.exceptions.specialty.SpecialtyInstanceAlreadyExistsException;
import com.sbproject.schedule.models.Specialty;

import java.util.List;
import java.util.Set;

public interface SpecialtyService {

    Specialty addSpecialty(String name, int year);
    void deleteSpecialty(Long id);
//    boolean deleteSpecialty(String name, int year);
    Specialty updateSpecialty(long id, String name, int year);
    Iterable<Specialty> getAll();
    Specialty getSpecialty(Long id);
    void deleteAll();

}
