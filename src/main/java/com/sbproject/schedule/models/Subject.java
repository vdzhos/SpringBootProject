package com.sbproject.schedule.models;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Subject {
    private Long id;
    private String name;
    private int quantOfGroups;
    private List<Teacher> teachers;
    private List<Specialty> specialties;

    public Subject() {
    }

    public Subject(Long id, String name, int quantOfGroups, List<Teacher> teachers, List<Specialty> specialties) {
        this.id = id;
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = teachers;
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantOfGroups=" + quantOfGroups +
                ", teachers: " + teachers +
                ", specialties: " + specialties + '}';
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

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

}
