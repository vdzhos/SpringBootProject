package com.sbproject.schedule.database;

import com.sbproject.schedule.models.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.util.*;

@Configuration
@ConditionalOnExpression("${custom.use-test-database:false}==false")
public class Database {

    /**
     * primitive db for specialties
     * @return
     */
    /*@Bean
    public Map<Long, Specialty> specialties(){
        Map<Long, Specialty> s = new HashMap<Long, Specialty>();
        s.put(1L, new Specialty("IPZ",4));
        s.put(2L, new Specialty("IPZ",1));
        s.put(3L, new Specialty("IPZ",2));
        return s;
    }*/

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
    /*@Bean
    public Map<Long, Subject> subjects(){
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialties().get(3L));
        Map<Long, Subject> s = new HashMap<Long, Subject>();
        s.put(1L, new Subject(1L,"Процедурне програмування", 6, new ArrayList<>(teachers().values()), specialties));
        s.put(2L, new Subject(2L,"Об'єктно-орієнтоване програмування", 6, new ArrayList<>(teachers().values()), specialties));
        return s;
    }*/
    
    /**
     * primitive db for users
     * @return
     */
   /* @Bean
    public Map<String, User> users() {
    	Map<String, User> s = new HashMap<String, User>();
        s.put("vovan", new User("vovan", "1234", false));
        s.put("ilya", new User("ilya", "4321", true));
        return s;
    }  */
    

    /*@Bean
    public Map<Long, Lesson> lessons(){
        Map<Long, Lesson> l = new HashMap<Long, Lesson>();
        l.put(1L, new Lesson(1L, Lesson.Time.TIME1, subjects().get(1L), teachers().get(1L),
                Lesson.SubjectType.LECTURE,"1-15", Lesson.Room.REMOTELY, DayOfWeek.MONDAY));
        l.put(2L, new Lesson(2L, Lesson.Time.TIME2, subjects().get(2L), teachers().get(3L),
                Lesson.SubjectType.PRACTICE.setGroup(1),"2-14", Lesson.Room.ROOM.setRoom("215"), DayOfWeek.MONDAY));
        return l;
    }*/

}
