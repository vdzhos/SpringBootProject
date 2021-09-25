package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Specialty;

public interface SpecialtyService {

    boolean addSpecialty(String name, int year);
    void deleteSpecialty(Long id);
//    boolean deleteSpecialty(String name, int year);
    boolean updateSpecialty(String name, int year);
    Iterable<Specialty> getAll();

}
