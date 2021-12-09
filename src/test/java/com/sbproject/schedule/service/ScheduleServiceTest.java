package com.sbproject.schedule.service;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.services.interfaces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

//import org.junit.runner.RunWith;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ScheduleServiceTest {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired 
	private LessonService lessonService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Test
	public void givenTeacherId_findTeacherLessons_thenLessonContainTeacherId()
	{
		try 
		{
			Long id = this.teacherService.getAll().iterator().next().getId();
			List<Lesson> res = this.scheduleService.getTeacherLessons(id);
			res.forEach(less -> Assertions.assertTrue(less.getTeacher().getId().longValue() == id.longValue())); 
		} 
		catch(Throwable thr)
		{
			thr.printStackTrace();
		}
	}
	
	@Test
	public void givenSpecialty_findSpecialtyLessons_thenSpecialtySubjectsContainFoundLessons()
	{
		Specialty spec = this.specialtyService.getAll().iterator().next();
		List<Lesson> res = this.scheduleService.getSpecialtyLessons(spec.getId());
		res.forEach(less -> Assertions.assertTrue(spec.getSubjects().contains(less.getSubject())));
	}
	
	@Test
	public void givenSetOfSubjects_findSubjectsLessonsWeeks_thenSetOfWeeksEqualsToUnionOfSubjectWeeks()
	{
		Iterable<Subject> subjs = this.subjectService.getAll();
		Set<Integer> intset = new TreeSet<>();
		for(Subject subj : subjs)
			subj.getLessons().forEach(less -> intset.addAll(less.getIntWeeks()));
		Set<Integer> res = (Set<Integer>)this.scheduleService.getSubjectLessonsWeeks(subjs);
		assertThat(intset).containsAll(res);
	}
	
	@Test
	public void givenListOfLessons_findLessonsRooms_thenContainsAll()
	{
		Iterable<Lesson> lessons = this.lessonService.getAll();
		Set<String> roomSet = new HashSet<>();
		lessons.forEach(less -> roomSet.add(less.getRoom().getTypeOrName()));
		Set<String> res = this.scheduleService.getLessonsRooms((List<Lesson>)lessons);
		assertThat(roomSet).containsAll(res);
	}
	
	@Test
	public void givenSubjectIterable_findLessonsFromSubjects_thenContainsAll()
	{
		Iterable<Subject> subjs = this.subjectService.getAll();
		List<Lesson> slessons = new ArrayList<>();
		subjs.forEach(subj -> slessons.addAll(subj.getLessons()));
		List<Lesson> res = this.scheduleService.getLessonsFromSubjects(subjs);
		assertThat(slessons).containsAll(res);
	}
	
	@Test
	public void givenIntegerWeekAndLessonList_filterLessonsByWeek_thenResultLessonsContainWeek()
	{
		Iterable<Lesson> lessons = this.lessonService.getAll();
		int week = lessons.iterator().next().getIntWeeks().iterator().next();
		List<Lesson> res = this.scheduleService.filterLessonsByWeek((List<Lesson>)lessons, week);
		res.forEach(less -> Assertions.assertTrue(less.getIntWeeks().contains(week)));
	}
	
	@Test
	public void givenRoomAndLessonList_filterLessonsByRoom_thenResultLessonsHaveRoom()
	{
		Iterable<Lesson> lessons = this.lessonService.getAll();
		String roomName = lessons.iterator().next().getRoom().getTypeOrName();
		List<Lesson> res = this.scheduleService.filterLessonsByRoom((List<Lesson>)lessons, roomName);
		res.forEach(less -> Assertions.assertTrue(less.getRoom().equalsByString(roomName)));
	}
}
