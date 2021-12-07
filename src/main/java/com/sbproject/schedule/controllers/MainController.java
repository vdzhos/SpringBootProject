package com.sbproject.schedule.controllers;

import com.sbproject.schedule.models.Lesson;
import com.sbproject.schedule.models.Schedule;
import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.services.implementations.SpecialtyServiceImpl;
import com.sbproject.schedule.services.implementations.SubjectServiceImpl;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.services.interfaces.*;
import com.sbproject.schedule.xlsx.ScheduleDownloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * loads all the data in all the tabs on the settings page
 */

@Controller
//I assume that main page is accessible only for admins
//@PreAuthorize("hasAnyRole('ADMIN')")
public class MainController {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScheduleReaderSaverService readerSaverService;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${custom.file-url}")
    private String fileUrl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAllAdmin(Model model){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        model.addAttribute("subjects",subjectService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        if(model.getAttribute("tab") == null){
            model.addAttribute("tab",0);
        }
        if(model.getAttribute("schedule") == null){
            model.addAttribute("schedule", new Schedule((List<Lesson>) lessonService.getAll()));
        }
        if(model.getAttribute("lessonSpec") == null){
            model.addAttribute("lessonSpec", -1);
        }

        return "editSchedule";
    }

    @PreAuthorize("hasAnyRole('REGULAR', 'ADMIN')")
    @GetMapping
    public String showAllSchedules(Model model,Authentication authentication){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        boolean adminLoggedIn = false;
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            adminLoggedIn = true;
        }
        model.addAttribute("adminLoggedIn",adminLoggedIn);
        model.addAttribute("lessons",(List<Lesson>)lessonService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
//        model.addAttribute("schedule",new Schedule((List<Lesson>) lessonService.getAll()));
        return "mainPage";
    }

//    @GetMapping("/schedule")
//    public RedirectView showSchedule(@RequestParam Long specialtyId, RedirectAttributes redir) {
//        RedirectView redirectView= new RedirectView("/",true);
//
//        redir.addFlashAttribute("specialtyIdToShow", specialtyId);
//
//        return redirectView;
//    }

    @PreAuthorize("hasAnyRole('REGULAR', 'ADMIN')")
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
//            FileInputStream fis = new FileInputStream(fileUrl + fileName1);
//            int len;
//            byte[] buf = new byte[1024];
//            while((len = fos.read(buf)) > 0){
//                bos.write(buf,0,len);
//            }
            ScheduleDownloader sd = new ScheduleDownloader("Schedule1");
            sd.downloadSchedule(this.lessonService.getAll(), bos);
            bos.close();
            
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadSchedule")
    public RedirectView uploadSchedule(@RequestParam("file") MultipartFile file, @RequestParam(required = false) Long specialtyId, RedirectAttributes redir) throws Exception {
        RedirectView redirectView= new RedirectView("/",true);
        String notification = "Розклад було успішно додано!";
        boolean success = true;
        try {
            readerSaverService.readSaveSchedule(file.getInputStream(),specialtyId);
        }catch (Exception e){
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        return redirectView;
    }



//    @GetMapping
//    public String loginPage(Authentication authentication) {
//        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            return "redirect:/admin";
//        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REGULAR"))) {
//            return "redirect:/user";
//        }
//        return "login";
//    }

}
