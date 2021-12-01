package com.sbproject.schedule.repositories;

import com.sbproject.schedule.models.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    Iterable<Teacher> findByName(String s);


    // Not tested
    @Query("select case when count(s)> 0 then true else false end from Teacher s where lower(s.name) like lower(concat('%', :surname,'%'))")
    boolean existsBySurname(@Param("surname") String surname);
}
