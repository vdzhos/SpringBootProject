package com.sbproject.schedule.database;

import com.sbproject.schedule.models.Specialty;
import com.sbproject.schedule.models.Subject;
import com.sbproject.schedule.models.Teacher;
import com.sbproject.schedule.models.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Configuration
public class Database {

    public static Long getUniqueId() {
        return new Random().nextLong()+System.currentTimeMillis();
    }

    /**
     * primitive db for specialties
     * @return
     */
    @Bean
    public Map<Long, Specialty> specialties(){
        Map<Long, Specialty> s = new HashMap<Long, Specialty>();
        s.put(1L, new Specialty(1L,"IPZ",4));
        s.put(2L, new Specialty(2L,"IPZ",1));
        return s;
    }

    /**
     * primitive db for teachers
     * @return
     */
    @Bean
    public Map<Long, Teacher> teachers(){
        Map<Long, Teacher> t = new HashMap<Long, Teacher>();
        t.put(1L, new Teacher(1L,"Бублик Володимир Васильович"));
        t.put(2L, new Teacher(2L,"Вовк Наталя Євгенівна"));
        t.put(3L, new Teacher(3L,"Горборуков В'ячеслав Вікторович"));
        return t;
    }

    /**
     * primitive db for subjects
     * @return
     */
    @Bean
    public Map<Long, Subject> subjects(){
        Map<Long, Subject> s = new HashMap<Long, Subject>();
        s.put(1L, new Subject(1L,"Процедурне програмування", 6, new ArrayList<>(teachers().values())));
        s.put(2L, new Subject(2L,"Об'єктно-орієнтоване програмування", 6, new ArrayList<>(teachers().values())));
        return s;
    }
    
    /**
     * primitive db for users
     * @return
     */
    @Bean
    public Map<String, User> users() {
    	Map<String, User> s = new HashMap<String, User>();
        s.put("vovan", new User("vovan", "1234", false));
        s.put("ilya", new User("ilya", "4321", true));
        return s;
    }
    

}
