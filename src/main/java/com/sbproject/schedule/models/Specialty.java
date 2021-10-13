package com.sbproject.schedule.models;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SPECIALTY")
public class Specialty {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Year", nullable = false)
    private int year;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "specialties_subjects",
            joinColumns = @JoinColumn(name = "specialty_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "subject_id", nullable = false)
    )
    private List<Subject> subjects;

    public Specialty() {
    }

    public Specialty(String name, int year) {
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
