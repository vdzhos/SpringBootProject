package com.sbproject.schedule.init;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.repositories.SpecialtyRepository;
import com.sbproject.schedule.repositories.SubjectRepository;
import com.sbproject.schedule.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {

    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        long countUsers = userRepository.count();

//        if(countUsers == 0) {
//        	User u1 = new User("vovan", "1234", Role.ADMIN);
//        	User u2 = new User("illya", "4321", Role.REGULAR);
//        	userRepository.save(u1);
//        	userRepository.save(u2);
//        }

        addSubjects();
        addSpecialties();
        addTeachers();
        assignTeachers();
    }

    private void assignTeachers() {

        Teacher t = teacherRepository.findByName("Teacher 1").iterator().next();

        Iterable<Subject> subjects = subjectRepository.findAll();
        for(Subject s: subjects){
            System.out.println("Teacher added");
            s.addTeacher(t);
            subjectRepository.save(s);
        }
    }

    private void addTeachers() {
        Teacher t = new Teacher("Teacher 1");
        Teacher t1 = new Teacher("Teacher 2");
        Teacher t2 = new Teacher("Teacher 3");

        teacherRepository.save(t);
        teacherRepository.save(t1);
        teacherRepository.save(t2);
    }

    private void addSubjects() {
        Subject s = new Subject("Subject 1", 3);
        Subject s1 = new Subject("Subject 2", 4);
        Subject s2= new Subject("Subject 3", 7);

        subjectRepository.save(s);
        subjectRepository.save(s1);
        subjectRepository.save(s2);
    }

    private void addSpecialties(){
        if (specialtyRepository.count() == 0) {
            Specialty sp1 =  new Specialty("IPZ",3);
            Specialty sp2 =  new Specialty("IPZ",4);
            Specialty sp3 =  new Specialty("KN",3);

            Subject s1 = subjectRepository.findByName("Subject 1").iterator().next();
            Subject s2 = subjectRepository.findByName("Subject 2").iterator().next();
            Subject s3 = subjectRepository.findByName("Subject 3").iterator().next();

            sp1.addSubject(s1);
            sp1.addSubject(s2);
            sp2.addSubject(s2);
            sp2.addSubject(s3);
            sp3.addSubject(s3);

            specialtyRepository.save(sp1);
            specialtyRepository.save(sp2);
            specialtyRepository.save(sp3);
            //specialtyRepository.findAll();
        }
    }

}
