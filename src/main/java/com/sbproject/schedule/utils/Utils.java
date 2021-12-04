package com.sbproject.schedule.utils;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;

import java.util.Set;

public interface Utils {

    String processName(String name);

    boolean isInvalidName(String name);

    void checkName(String name);

    void checkSubjectName(String name);

    public Long getUniqueId();

    void checkYear(int year);

    void checkQuantOfGroups(int quantOfGroups);

    void checkQuantOfSpecialties(int quantOfSpecialties);

    void checkSpecialties(Iterable<Subject> subjects, Set<Specialty> specialties);
}
