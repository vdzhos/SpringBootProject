package com.sbproject.schedule.exceptions.teacher;

public class NoTeacherWithSuchIdException extends Exception {

    private String delOrUpdOrGet;


    public NoTeacherWithSuchIdException(Long id, String delOrUpdOrGet) {
        super("Teacher with id '"+ id +"' doesn't exist!");
        this.delOrUpdOrGet = delOrUpdOrGet;
    }

    public String getDelOrUpdOrGet() {
        return delOrUpdOrGet;
    }

}
