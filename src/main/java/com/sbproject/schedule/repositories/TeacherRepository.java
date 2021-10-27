package com.sbproject.schedule.repositories;

import com.sbproject.schedule.models.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    Iterable<Teacher> findByName(String s);
}
