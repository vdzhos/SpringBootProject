package com.sbproject.schedule.services.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.interfaces.ScheduleService;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
    private SpecialtyService specialtyService;
    @Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;

	@Transactional
	@Override
	public List<Lesson> getTeacherLessons(Long id) throws Throwable {
		List<Lesson> lessons = new ArrayList<Lesson>();
		StreamSupport.stream(this.getTeacherSubjects(id).spliterator(), false)
		.forEach(subj -> lessons
				.addAll(subjectService.getSubjectLessons(subj.getId())
						.stream()
						.filter(less -> less.getTeacher().getId() == id)
						.collect(Collectors.toList())));
		return lessons;
	}

	@Override
	public List<Lesson> getSpecialtyLessons(Long id) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		StreamSupport.stream(this.getSpecialtySubjects(id).spliterator(), false)
			.forEach(subj -> lessons.addAll(subj.getLessons()));
		return lessons;
	}

	@Override
	public Specialty getSpecialty(Long id) {
		return this.specialtyService.getSpecialty(id);
	}

	@Override
	public Teacher getTeacher(Long id) throws Throwable {
		return this.teacherService.getTeacherById(id);
	}

	@Override
	public Iterable<Subject> getTeacherSubjects(Long id) throws Throwable {
		return this.getTeacher(id).getSubjects();
	}

	@Override
	public Iterable<Subject> getSpecialtySubjects(Long id) {
		return this.getSpecialty(id).getSubjects();
	}

	@Override
	public Iterable<Integer> getSubjectLessonsWeeks(Iterable<Subject> subjects) {
		return this.subjectService.getLessonWeeks(StreamSupport
						.stream(subjects.spliterator(), false)
						.map(sub -> sub.getId())
						.collect(Collectors.toSet()));
	}

	public Set<String> getLessonsRooms(List<Lesson> lessons)
	{
		Set<String> rooms = new HashSet<String>();
		lessons.stream().forEach(less -> rooms.add(less.getRoom().getTypeOrName()));
		return rooms;
	}
	
//filters 
	
	@Override
	public List<Lesson> getSubjectLessons(Long id) {
		return subjectService
				.getSubjectById(id)
				.getLessons();
	}

	/*@Override
	public List<Lesson> getSubjectLessonsByTeacher(Long subjId, Long teachId) {
		return this.getSubjectLessons(subjId)
				.stream()
				.filter(less -> less.getTeacher().getId() == teachId)
				.collect(Collectors.toList());
	}*/

	@Override
	public List<Lesson> filterLessonsByWeek(List<Lesson> list, int week) {
		return list
				.stream()
				.filter(less -> less.getIntWeeks().contains(week))
				.collect(Collectors.toList());
	}

	@Override
	public List<Lesson> filterLessonsByRoom(List<Lesson> list, String room) {
		return list
				.stream()
				.filter(less -> less.getRoom().equalsByString(room))
				.collect(Collectors.toList());
	}

	@Override
	public List<Lesson> getLessonsFromSubjects(Iterable<Subject> subjects) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		StreamSupport.stream(subjects.spliterator(), false).forEach(subj -> lessons.addAll(subj.getLessons()));
		return lessons;
	}

	@Override
	public Set<Teacher> getTeachersFromSubjects(Iterable<Subject> subjects) {
		Set<Teacher> res = new TreeSet<>();
		subjects.forEach(subj -> res.addAll(subj.getTeachers()));
		return res;
	}

}
