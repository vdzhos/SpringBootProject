package com.sbproject.schedule.repositories;

import com.sbproject.schedule.models.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Long> {

    Iterable<Subject> findByName(String name);

}
