package com.sbproject.schedule.controllers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.sbproject.schedule.models.Schedule;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.xlsx.ScheduleDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.interfaces.ScheduleService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/view")
public class ScheduleTableController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private SpecialtyService specialtyService;
	
	private Iterable<Subject> subjects;
	
	private Iterable<Integer> weeks;
	
	private Set<String> rooms;
	
	private List<Lesson> lessons;
	
	private Set<Teacher> teachers;
	
	private Long teacherId;
	
	private String entityName;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@GetMapping("/specialty")
	public String getSpecialtySchedule(@RequestParam Long specialtyId, Model model) throws Throwable
	{
		this.teacherId = null;
		initContainers(false, specialtyId, model);
		this.teachers = this.scheduleService.getTeachersFromSubjects(subjects);
		model.addAttribute("forSpecialty", true);
		model.addAttribute("teachers", teachers);
		return "scheduleTablePage";
	}


	@GetMapping("/download")
	@ResponseBody
	public void download(HttpServletResponse response){
		String fileName1 = entityName + ".xlsx";
		String fileName2 = URLEncoder.encode(fileName1, StandardCharsets.UTF_8);
		response.setContentType("application/ms-excel; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition","attachment; filename="+fileName2);
		response.setHeader("Content-Transfer-Encoding","binary");
		try{
			BufferedOutputStream bos =new BufferedOutputStream(response.getOutputStream());
			ScheduleDownloader sd = new ScheduleDownloader(entityName);
			Collections.sort(lessons);
			sd.downloadSchedule(lessons, bos);
			bos.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/teacher")
	public String getTeacherSchedule(@RequestParam Long teacherId, Model model) throws Throwable
	{
		this.teacherId = teacherId;
		this.teachers = null;
		initContainers(true, teacherId, model);
		model.addAttribute("forSpecialty", false);
		return "scheduleTablePage";
	}
	
	@GetMapping("/filterSchedule")
	public String applyFilters(@RequestParam String subjectId, @RequestParam String teacherId, @RequestParam int week, @RequestParam String room, Model model)
	{
		if(!subjectId.equals("Not selected"))
			lessons = this.scheduleService.getSubjectLessons(Long.parseLong(subjectId));
		else if(subjects != null)
			lessons = this.scheduleService.getLessonsFromSubjects(subjects);
		
		if(this.teacherId != null)
			lessons.removeIf(less -> less.getTeacher().getId().longValue() != this.teacherId.longValue());
		else if(!teacherId.equals("Not selected"))
			lessons.removeIf(less -> less.getTeacher().getId().longValue() != Long.parseLong(teacherId));

		if(week != -1)
			lessons = this.scheduleService.filterLessonsByWeek(lessons, week);
		
		if(!room.equals("Not selected"))
			lessons = this.scheduleService.filterLessonsByRoom(lessons, room);
		
		model.addAttribute("schedule",new Schedule(lessons));
		model.addAttribute("appName",appName);
		model.addAttribute("entityName", entityName);
		model.addAttribute("subjects", this.subjects);
		model.addAttribute("rooms", rooms);
		model.addAttribute("weeks", this.weeks);
		model.addAttribute("forSpecialty", this.teacherId == null);
		if(this.teachers != null)
			model.addAttribute("teachers", teachers);
		return "scheduleTablePage";
	}
	

	private void initContainers(boolean forTeacher, Long id, Model model) throws Throwable
	{
		if(forTeacher)
		{
			entityName = this.scheduleService.getTeacher(id).getName();
			subjects = this.scheduleService.getTeacherSubjects(id);
			lessons = this.scheduleService.getTeacherLessons(id);
		}
		else
		{
			entityName = this.scheduleService.getSpecialty(id).toString();
			subjects = specialtyService.getSpecialtySubjects(id);
//			subjects = this.scheduleService.getSpecialtySubjects(id);
			lessons = specialtyService.getSpecialtyLessons(id);
//			lessons = this.scheduleService.getSpecialtyLessons(id);
		}
		weeks = this.scheduleService.getSubjectLessonsWeeks(subjects);

		rooms = this.scheduleService.getLessonsRooms(lessons);
		model.addAttribute("appName",appName);
		model.addAttribute("entityName", entityName);
		model.addAttribute("subjects", subjects);
		model.addAttribute("weeks", weeks);
		model.addAttribute("rooms", rooms);
		model.addAttribute("schedule",new Schedule(lessons));
	}
}
