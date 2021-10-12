package com.sbproject.schedule.init;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.repositories.SpecialtyRepository;
import com.sbproject.schedule.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {

    private SpecialtyRepository specialtyRepository;
//    private UserRepository userRepository;
    
    @Autowired
    public DataInit(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = specialtyRepository.count();
//        long countUsers = userRepository.count();
        if (count == 0) {
            Specialty s1 =  new Specialty("IPZ",3);
            Specialty s2 =  new Specialty("IPZ",4);
            Specialty s3 =  new Specialty("KN",3);

            specialtyRepository.save(s1);
            specialtyRepository.save(s2);
            specialtyRepository.save(s3);
        }
//        if(countUsers == 0) {
//        	User u1 = new User("vovan", "1234", Role.ADMIN);
//        	User u2 = new User("illya", "4321", Role.REGULAR);
//        	userRepository.save(u1);
//        	userRepository.save(u2);
//        }

    }
}
