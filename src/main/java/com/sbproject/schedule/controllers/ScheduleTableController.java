package com.sbproject.schedule.controllers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.sbproject.schedule.models.Schedule;
import com.sbproject.schedule.xlsx.ScheduleDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/view")
public class ScheduleTableController {

	@Autowired
    private SpecialtyService specialtyService;
    @Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;
	
	private Iterable<Subject> subjects;
	
	private Iterable<Integer> weeks;
	
	private Set<String> rooms;
	
	private List<Lesson> lessons;
	
	private Long teacherId;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@GetMapping("/specialty")
	public String getSpecialtySchedule(@RequestParam Long specialtyId, Model model) throws Throwable
	{
		this.teacherId = null;
		initContainers(false, specialtyId, model);
		return "scheduleTablePage";
	}


	@GetMapping("/download")
	@ResponseBody
	public void download(HttpServletResponse response){
		String fileName1 = "Schedule_example.xlsx";
		String fileName2 = URLEncoder.encode(fileName1, StandardCharsets.UTF_8);
		response.setContentType("application/ms-excel; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition","attachment; filename="+fileName2);
		response.setHeader("Content-Transfer-Encoding","binary");
		try{
			BufferedOutputStream bos =new BufferedOutputStream(response.getOutputStream());
			ScheduleDownloader sd = new ScheduleDownloader("Schedule1");
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
		initContainers(true, teacherId, model);
		return "scheduleTablePage";
	}
	
	@GetMapping("/filterSchedule")
	public String applyFilters(@RequestParam String subjectId, @RequestParam int week, @RequestParam String room, Model model)
	{
		if(!subjectId.equals("Not selected"))
		{
			lessons = subjectService
				.getSubjectById(Long.parseLong(subjectId))
				.getLessons();
			if(teacherId != null)
				this.lessons.removeIf(less -> less.getTeacher().getId() != this.teacherId);
		}
		else
		{
			if(lessons != null)
				lessons.clear();
			StreamSupport.stream(this.subjects.spliterator(), false).forEach(subj -> lessons.addAll(subj.getLessons()));
		}
		if(week != -1)
		{
			lessons.removeIf(less -> !less.getIntWeeks().contains(week));
		}
		if(!room.equals("Not selected"))
		{
			lessons.removeIf(less -> !less.getRoom().equalsByString(room));
		}
		model.addAttribute("schedule",new Schedule(lessons));
		model.addAttribute("appName",appName);
//		model.addAttribute("lessons", lessons);
		model.addAttribute("subjects", this.subjects);
		model.addAttribute("rooms", rooms);
		model.addAttribute("weeks", this.weeks);
		return "scheduleTablePage";
	}
	
	
	private void initContainers(boolean forTeacher, Long id, Model model) throws Throwable
	{
		lessons = new ArrayList<Lesson>();
		
		if(forTeacher)
		{
			Teacher teach = this.teacherService.getTeacherById(id);
			subjects = teach.getSubjects();
			StreamSupport.stream(this.subjects.spliterator(), false)
			.forEach(subj -> lessons
					.addAll(subj
							.getLessons()
							.stream()
							.filter(less -> less.getTeacher().getId() == teach.getId())
							.collect(Collectors.toList())));
		}
		else
		{
			subjects = this.specialtyService.getSpecialty(id).getSubjects();
			StreamSupport.stream(this.subjects.spliterator(), false).forEach(subj -> lessons.addAll(subj.getLessons()));
		}
		weeks = this.subjectService.getLessonWeeks(StreamSupport
				.stream(subjects.spliterator(), false)
				.map(sub -> sub.getId())
				.collect(Collectors.toSet()));
		this.rooms = new HashSet<String>();
		lessons.stream().forEach(less -> rooms.add(less.getRoom().getTypeOrName()));
		model.addAttribute("appName",appName);
		model.addAttribute("subjects", subjects);
		model.addAttribute("weeks", weeks);
		model.addAttribute("rooms", rooms);
		model.addAttribute("schedule",new Schedule(lessons));
	}
}
