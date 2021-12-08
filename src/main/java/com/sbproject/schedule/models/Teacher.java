package com.sbproject.schedule.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sbproject.schedule.utils.EntityIdResolver;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Subject.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "subject_id", nullable = false))
    @NotNull
    private Set<Subject> subjects;



    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Lesson> lessons;

    public Teacher(String name) {
        this.name = name;
        subjects = new HashSet<>();
    }


    public Teacher() {
    }

    public Teacher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher(String name, Set<Subject> subjects) {
        this.subjects = subjects;
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "Teacher{" +
//                "id=" + id +
//                ", name='" + name + '}';
//    }


    @Override
    public String toString() {
        return name;
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
}
