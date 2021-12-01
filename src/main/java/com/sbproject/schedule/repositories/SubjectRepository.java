package com.sbproject.schedule.repositories;

import com.sbproject.schedule.models.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Long> {

    Iterable<Subject> findByName(String name);


    // Not tested
    @Query("select case when count(s)> 0 then true else false end from Subject s where lower(s.name) like lower(concat('%', :name,'%'))")
    boolean existsByName(@Param("name") String name);


}
