package com.sbproject.schedule.utils;

import com.sbproject.schedule.exceptions.specialty.InvalidSpecialtyNameException;

public interface Utils {

    String processName(String name);

    boolean isInvalidName(String name);

    void checkName(String name) throws InvalidSpecialtyNameException;

    public Long getUniqueId();
}
