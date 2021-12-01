package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.SubjectType;
import com.sbproject.schedule.services.interfaces.LessonService;
import com.sbproject.schedule.services.interfaces.SpecialtyService;
import com.sbproject.schedule.services.interfaces.SubjectService;
import com.sbproject.schedule.services.interfaces.TeacherService;
import com.sbproject.schedule.xlsx.ScheduleAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleReaderSaverService {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SpecialtyService specialtyService;



    public void readSaveSchedule(InputStream inputStream, long specialtyId) {
        List<ScheduleAnalyzer.LessonInfo> lessons = read(inputStream);
        Specialty s = specialtyService.getSpecialty(specialtyId);
        save(lessons, s);
    }

    private void save(List<ScheduleAnalyzer.LessonInfo> lessons,Specialty s) {
        for (ScheduleAnalyzer.LessonInfo li: lessons) {
            save(li,s);
        }
    }

    private void save(ScheduleAnalyzer.LessonInfo li, Specialty s) {
        Subject subject = saveSubject(li,s);
        saveTeacher(li,subject);

    }

    private void saveTeacher(ScheduleAnalyzer.LessonInfo li, Subject subject) {

    }

    private Subject saveSubject(ScheduleAnalyzer.LessonInfo li, Specialty s) {
        String subjectName = li.getSubject();
        SubjectType type = li.getGroup();
        int quantityOfGroups = 0;
        if (type.getType() == SubjectType.SubjectTypeEnum.PRACTICE) {
            quantityOfGroups = Integer.parseInt(type.getGroup());
        }
        if (subjectService.subjectExistsByName(subjectName)) {
            Subject subject = subjectService.getSubjectByName(li.getSubject());
            if (!subject.getSpecialties().contains(s)) {
                subject.addSpecialty(s);
            }
            if (subject.getQuantOfGroups() < quantityOfGroups) {
                subject.setQuantOfGroups(quantityOfGroups);
            }
            return subjectService.updateSubjectNoCheck(subject);
        } else {
            Subject subject = new Subject(subjectName,quantityOfGroups);
            subject.addSpecialty(s);
            return subjectService.addSubject(subject);
        }
    }


    private List<ScheduleAnalyzer.LessonInfo> read(InputStream inputStream) {
        ScheduleAnalyzer analyzer = new ScheduleAnalyzer(inputStream);
        analyzer.analyze();
        return analyzer.getLessons();
    }



}
