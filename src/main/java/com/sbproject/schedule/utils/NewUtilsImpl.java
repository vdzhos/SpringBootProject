package com.sbproject.schedule.utils;

import com.demo.customstarter.exceptions.InvalidSpecialtyNameException;
import com.demo.customstarter.utils.Utils;

public class NewUtilsImpl implements Utils {


    @Override
    public String processName(String name) {
        System.out.println("NEW UTILS IMPLEMENTATION");
        return name;
    }

    @Override
    public boolean isInvalidName(String name) {
        return false;
    }

    @Override
    public void checkName(String name) throws InvalidSpecialtyNameException {

    }

    @Override
    public Long getUniqueId() {
        return null;
    }
}
