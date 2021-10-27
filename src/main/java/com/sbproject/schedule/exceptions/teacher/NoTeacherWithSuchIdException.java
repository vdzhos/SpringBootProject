package com.sbproject.schedule.exceptions.teacher;

public class NoTeacherWithSuchIdException extends Exception {

    private String delOrUpd;


    public NoTeacherWithSuchIdException(Long id, String delOrUpd) {
        super("Teacher with id '"+ id +"' doesn't exist!");
        this.delOrUpd = delOrUpd;
    }

    public String getDelOrUpd() {
        return delOrUpd;
    }

}
