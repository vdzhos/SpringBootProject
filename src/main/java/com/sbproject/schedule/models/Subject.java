package com.sbproject.schedule.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantOfGroups;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private Set<Teacher> teachers;


    @ManyToMany(mappedBy = "subjects")
    private List<Specialty> specialties;


    public Subject() {
    }

    public Subject(String name, int quantOfGroups) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        teachers = new HashSet<>();
        specialties = new ArrayList<>();
    }


    public Subject(long id, String name, int quantOfGroups, List<Teacher> teachers, List<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
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

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public void addTeacher(Teacher t) {
        teachers.add(t);
    }
}
