package com.sbproject.schedule.services.interfaces;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import org.springframework.boot.configurationprocessor.json.JSONArray;

import java.util.List;

public interface SpecialtyService {

    Specialty addSpecialty(String name, int year);
    void deleteSpecialty(Long id);

    Specialty updateSpecialty(long id, String name, int year);
    Iterable<Specialty> getAll();
    Specialty getSpecialty(Long id);
    void deleteAll();
    Specialty addSpecialty(String name, int year, JSONArray subjects);
    Iterable<Subject> getSpecialtySubjects(Long specialtyId);

    List<Lesson> getSpecialtyLessons(Long id);
}
