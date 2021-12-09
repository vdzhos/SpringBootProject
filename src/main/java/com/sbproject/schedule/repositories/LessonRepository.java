package com.sbproject.schedule.repositories;

import com.sbproject.schedule.models.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends CrudRepository<Lesson,Long> {

}
