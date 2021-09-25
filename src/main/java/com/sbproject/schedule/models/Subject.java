package com.sbproject.schedule.models;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Subject {
    private Long id;
    private String name;
    private int quantOfGroups;
    private List<Teacher> teachers;

    public Subject() {
    }

    public Subject(Long id, String name, int quantOfGroups, List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantOfGroups=" + quantOfGroups +
                "teachers: " + teachers + '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantOfGroups() {
        return quantOfGroups;
    }

    public void setQuantOfGroups(int quantOfGroups) {
        this.quantOfGroups = quantOfGroups;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}
