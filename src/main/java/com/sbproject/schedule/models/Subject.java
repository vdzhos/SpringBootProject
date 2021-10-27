package com.sbproject.schedule.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "subjects_specialties",
            joinColumns = @JoinColumn(name = "subject_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "specialty_id", nullable = false)
    )
    private Set<Specialty> specialties;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    public Subject() {
    }

    public Subject(String name, int quantOfGroups) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = new HashSet<>();
    }

    public Subject(String name, int quantOfGroups, Set<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = specialties;
    }

    public Subject(long id, String name, int quantOfGroups, Set<Teacher> teachers,Set<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = teachers;
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return name;
    }

//    @Override
//    public String toString() {
//        return "Subject{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", quantOfGroups=" + quantOfGroups +
//                ", teachers: " + teachers +
//                ", specialties: " + specialties + '}';
//    }


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

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    public void addTeacher(Teacher t) {
        teachers.add(t);
    }

    public void addSpecialty(Specialty sp) {
        specialties.add(sp);
    }

}
