package com.sbproject.schedule.repositories;

import com.sbproject.schedule.models.Specialty;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends CrudRepository<Specialty, Long> {

    //List<Specialty> findByNameLike(String name);

    Iterable<Specialty> findByYear(int year);
    Iterable<Specialty> findByNameAndYear(String name, int year);
    Iterable<Specialty> findByName(String name);

    boolean existsByNameAndYear(String name, int year);

}
