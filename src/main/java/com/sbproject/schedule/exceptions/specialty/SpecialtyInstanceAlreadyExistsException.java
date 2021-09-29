package com.sbproject.schedule.exceptions.specialty;

import javax.management.InstanceAlreadyExistsException;

public class SpecialtyInstanceAlreadyExistsException extends InstanceAlreadyExistsException {
    public SpecialtyInstanceAlreadyExistsException(String message) {
        super(message);
    }
}
