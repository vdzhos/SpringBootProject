package com.sbproject.schedule.services.implementations;

import com.sbproject.schedule.exceptions.schedule.ScheduleException;
import com.sbproject.schedule.models.*;
import com.sbproject.schedule.services.interfaces.*;
import com.sbproject.schedule.xlsx.ScheduleAnalyzer;
import com.sbproject.schedule.xlsx.ScheduleAnalyzerOF;
import com.sbproject.schedule.xlsx.ScheduleAnalyzerStd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ScheduleReaderSaverServiceImpl implements ScheduleReaderSaverService {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SpecialtyService specialtyService;


    private byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int read = 0;
        byte[] buff = new byte[1024];
        while ((read = inputStream.read(buff)) != -1) {
            bos.write(buff, 0, read);
        }
        return bos.toByteArray();
    }

    @CacheEvict(cacheNames = {"specialties", "allSpecialties", "subjects", "allSubjects"}, allEntries = true)
    @Override
    public void readSaveSchedule(InputStream inputStream, long specialtyId) throws Exception {
        byte[] input = readInputStream(inputStream);
        List<ScheduleAnalyzer.LessonInfo> lessons = read(input);
        Specialty s = specialtyService.getSpecialty(specialtyId);

        save(lessons, s);
    }

    private void save(List<ScheduleAnalyzerStd.LessonInfo> lessons, Specialty s) throws Exception {
        for (ScheduleAnalyzerStd.LessonInfo li: lessons) {
            save(li,s);
        }
    }

    private void save(ScheduleAnalyzerStd.LessonInfo li, Specialty s) throws Exception {
        Subject subject = saveSubject(li,s);
        Teacher teacher = saveTeacher(li,subject);

        Lesson lesson = new Lesson(li.getTime(),
                subject,teacher,
                li.getGroup(),
                li.getWeeks(),
                li.getRoom(),
                li.getDay());
        lessonService.addLesson(lesson);
    }

    private Teacher saveTeacher(ScheduleAnalyzerStd.LessonInfo li, Subject subject) throws Exception {
        String teacherSurname = li.getTeacherSurname();
        Teacher teacher = null;

        Iterable<Teacher> teachers = teacherService.getTeacherByPartName(teacherSurname);

        for (Teacher t: teachers) {
            String[] name = t.getName().split("\\s+");
            if (name.length != 3)
                continue;
//                throw new ScheduleException("Incorrect teacher name: "+t.getName());
            if (name[1].startsWith(li.getTeacherFirstName()) && name[2].startsWith(li.getTeacherLastName())) {
                teacher = t;
                break;
            }
        }

        if (teacher != null){
            if (!teacher.getSubjects().contains(subject)){
                teacher.addSubject(subject);
                return teacherService.updateTeacherNoCheck(teacher);
            }
            return teacher;
        } else {
            teacher = new Teacher(teacherSurname+" "+li.getTeacherFirstName()+" "+li.getTeacherLastName());
            teacher.addSubject(subject);
            return teacherService.addTeacher(teacher);
        }
    }

    private Subject saveSubject(ScheduleAnalyzerStd.LessonInfo li, Specialty s) {
        String subjectName = li.getSubject();
        SubjectType type = li.getGroup();
        int quantityOfGroups = 1;
        if (type.getType() == SubjectType.SubjectTypeEnum.PRACTICE) {
            quantityOfGroups = Integer.parseInt(type.getGroup());
        }
        if (subjectService.subjectExistsByName(subjectName)) {
            Subject subject = subjectService.getSubjectByName(li.getSubject());
            boolean changed = false;
            if (!subject.getSpecialties().contains(s)) {
                subject.addSpecialty(s);
                changed = true;
            }
            if (subject.getQuantOfGroups() < quantityOfGroups) {
                subject.setQuantOfGroups(quantityOfGroups);
                changed = true;
            }
            if (changed) return subjectService.updateSubjectNoCheck(subject);
            return subject;
        } else {
            Subject subject = new Subject(subjectName,quantityOfGroups);
            subject.addSpecialty(s);
            return subjectService.addSubject(subject);
        }
    }


    private List<ScheduleAnalyzer.LessonInfo> read(byte[] bytes) {
        ScheduleAnalyzer analyzer = new ScheduleAnalyzerStd(putBytesToInputStream(bytes));
        try {
            analyzer.analyze();
        } catch (ScheduleException e) {
            try {
                System.out.println("TRYING TO PARSE OUR FORMAT");
                analyzer = new ScheduleAnalyzerOF(putBytesToInputStream(bytes));
                analyzer.analyze();
            } catch (ScheduleException ee) {
                throw new ScheduleException(e.getMessage()+' '+ee.getMessage());
            }
        }
        return analyzer.getLessons();
    }

    private InputStream putBytesToInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

}
