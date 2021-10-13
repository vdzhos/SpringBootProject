package com.sbproject.schedule.init;

import com.sbproject.schedule.models.*;
import com.sbproject.schedule.repositories.LessonRepository;
import com.sbproject.schedule.repositories.SpecialtyRepository;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class DataInit implements ApplicationRunner {

    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        long countUsers = userRepository.count();

//        if(countUsers == 0) {
//        	User u1 = new User("vovan", "1234", Role.ADMIN);
//        	User u2 = new User("illya", "4321", Role.REGULAR);
//        	userRepository.save(u1);
//        	userRepository.save(u2);
//        }

        addSpecialties();
        addSubjects();
        addTeachers();
        addLessons();
    }


    private void addTeachers() {
        Teacher t = new Teacher("Teacher 1");
        Teacher t1 = new Teacher("Teacher 2");
        Teacher t2 = new Teacher("Teacher 3");

        Subject subject = subjectRepository.findByName("Subject 1").iterator().next();
        Subject subject1 = subjectRepository.findByName("Subject 2").iterator().next();

        t.addSubject(subject);
        t.addSubject(subject1);

        t1.addSubject(subject);

        t2.addSubject(subject1);

        teacherRepository.save(t);
        teacherRepository.save(t1);
        teacherRepository.save(t2);
    }

    private void addSubjects() {
        Subject s = new Subject("Subject 1", 3);
        Subject s1 = new Subject("Subject 2", 4);
        Subject s2 = new Subject("Subject 3", 7);

        Specialty sp1 = specialtyRepository.findByNameAndYear("IPZ", 3).iterator().next();
        Specialty sp2 = specialtyRepository.findByNameAndYear("IPZ", 4).iterator().next();
        Specialty sp3 = specialtyRepository.findByNameAndYear("KN", 3).iterator().next();

        s.addSpecialty(sp2);
        s.addSpecialty(sp1);
        s1.addSpecialty(sp2);
        s1.addSpecialty(sp3);
        s2.addSpecialty(sp3);

        subjectRepository.save(s);
        subjectRepository.save(s1);
        subjectRepository.save(s2);
    }

    private void addSpecialties(){
        Specialty sp1 =  new Specialty("IPZ",3);
        Specialty sp2 =  new Specialty("IPZ",4);
        Specialty sp3 =  new Specialty("KN",3);

        specialtyRepository.save(sp1);
        specialtyRepository.save(sp2);
        specialtyRepository.save(sp3);
        //specialtyRepository.findAll();
    }

    private void addLessons(){

        Subject s = subjectRepository.findByName("Subject 1").iterator().next();
        Subject s2 = subjectRepository.findByName("Subject 2").iterator().next();

        Teacher t1 = teacherRepository.findByName("Teacher 1").iterator().next();
        Teacher t2 = teacherRepository.findByName("Teacher 2").iterator().next();
        Teacher t3 = teacherRepository.findByName("Teacher 3").iterator().next();

        Lesson l1 = new Lesson(Lesson.Time.TIME1, s, t1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson l2 = new Lesson(Lesson.Time.TIME2, s, t2, new SubjectType(1), "1-15", new Room("216"), DayOfWeek.MONDAY);
        Lesson l3 = new Lesson(Lesson.Time.TIME3, s2, t3, new SubjectType(2), "1-15", new Room("216"), DayOfWeek.MONDAY);

        lessonRepository.save(l1);
        lessonRepository.save(l2);
        lessonRepository.save(l3);
    }


}
