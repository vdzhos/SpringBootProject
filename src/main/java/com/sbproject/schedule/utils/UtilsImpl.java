package com.sbproject.schedule.utils;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;

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


}
