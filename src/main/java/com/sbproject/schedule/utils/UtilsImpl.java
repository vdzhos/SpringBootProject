package com.sbproject.schedule.utils;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.exceptions.specialty.SpecialtyIllegalArgumentException;
import com.sbproject.schedule.exceptions.subject.InvalidSubjectNameException;
import com.sbproject.schedule.exceptions.subject.SubjectIllegalArgumentException;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class UtilsImpl  implements Utils {

    @Override
    public String processName(String name){
        return name.replaceAll("\\s+"," ").trim();
    }

    @Override
    public boolean isInvalidName(String name) {
        return name.isEmpty();
    }

    @Override
    public void checkName(String name) {
        if(isInvalidName(name)){
            throw new InvalidSpecialtyNameException(Values.INVALID_SPECIALTY_NAME);
        }
    }

    @Override
    public void checkSubjectName(String name) {
        if (isInvalidName(name)) {
            throw new InvalidSubjectNameException(Values.INVALID_SUBJECT_NAME);
        }
    }

    @Override
    public Long getUniqueId() {
        return new Random().nextLong()+System.currentTimeMillis();
    }

    @Override
    public void checkYear(int year) {
        if(year>Values.MAX_YEAR||year<Values.MIN_YEAR){
            throw new SpecialtyIllegalArgumentException("Specialty year = " + year+" is out of bounds "+Values.MIN_YEAR+" - "+Values.MAX_YEAR);
        }
    }

    @Override
    public void checkQuantOfGroups(int quantOfGroups) {
        if(quantOfGroups > Values.MAX_QUANT_OF_GROUPS || quantOfGroups < Values.MIN_QUANT_OF_GROUPS){
            throw new SubjectIllegalArgumentException("Subject quantity of groups = " + quantOfGroups + " is out of bounds " + Values.MIN_QUANT_OF_GROUPS + " - "+ Values.MAX_QUANT_OF_GROUPS);
        }
    }

    @Override
    public void checkQuantOfSpecialties(int quantOfSpecialties) {
        if(quantOfSpecialties < Values.MIN_QUANT_OF_SPECIALTIES_ON_SUBJECT){
            throw new SubjectIllegalArgumentException("Subject quantity of specialties = " + quantOfSpecialties + " is incorrect - less than " + Values.MIN_QUANT_OF_GROUPS);
        }
    }

    @Override
    public void checkSpecialties(Iterable<Subject> subjects, Set<Specialty> specialties) {
        for (Subject subject: subjects) {
            for (Specialty specialty: subject.getSpecialties()) {
                if (specialties.contains(specialty))
                    throw new SubjectIllegalArgumentException("Subject with such name already exists on specialty " + specialty);
            }
        }
    }

}
