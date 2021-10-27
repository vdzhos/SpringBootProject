package com.sbproject.schedule.utils;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;
import com.sbproject.schedule.exceptions.specialty.SpecialtyIllegalArgumentException;

import java.util.Random;

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
    public void checkName(String name) throws InvalidSpecialtyNameException {
        if(isInvalidName(name)){
            throw new InvalidSpecialtyNameException(Values.INVALID_SPECIALTY_NAME);
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


}
