package com.sbproject.schedule.services.interfaces;

import java.util.List;
import java.util.Set;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;

public interface ScheduleService {

	List<Lesson> getTeacherLessons(Long id) throws Throwable;
	
	List<Lesson> getSpecialtyLessons(Long id);
	
	Specialty getSpecialty(Long id);
	
	Teacher getTeacher(Long id) throws Throwable;
	
	Iterable<Subject> getTeacherSubjects(Long id) throws Throwable;
	
	Iterable<Subject> getSpecialtySubjects(Long id);
	
	Iterable<Integer> getSubjectLessonsWeeks(Iterable<Subject> subjects);
	
	Set<String> getLessonsRooms(List<Lesson> list);
	
//filters
	
	List<Lesson> getLessonsFromSubjects(Iterable<Subject> subjects);
	
	List<Lesson> getSubjectLessons(Long id);
	
	List<Lesson> filterLessonsByWeek(List<Lesson> list, int week);
	
	List<Lesson> filterLessonsByRoom(List<Lesson> list, String room);
	
}
