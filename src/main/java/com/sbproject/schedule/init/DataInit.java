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

        addSpecialties();
        addSubjects();
        addTeachers();
        assignTeachers();
        //assignSubjects();
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

        subjectRepository.save(s);
        subjectRepository.save(s1);
        subjectRepository.save(s2);
    }

    private void addSpecialties(){
        if (specialtyRepository.count() == 0) {
            Specialty s1 =  new Specialty("IPZ",3);
            Specialty s2 =  new Specialty("IPZ",4);
            Specialty s3 =  new Specialty("KN",3);

            specialtyRepository.save(s1);
            specialtyRepository.save(s2);
            specialtyRepository.save(s3);
            //specialtyRepository.findAll();
        }
    }

    private void assignSubjects() {

        Subject s = subjectRepository.findByName("Subject 1").iterator().next();

        Iterable<Specialty> specialties = specialtyRepository.findAll();
        for(Specialty sp: specialties){
            sp.addSubject(s);
            specialtyRepository.save(sp);
        }

        Specialty sp1 = specialtyRepository.findByNameAndYear("IPZ", 3).iterator().next();
        sp1.addSubject(subjectRepository.findByName("Subject 2").iterator().next());
        specialtyRepository.save(sp1);
    }


}
