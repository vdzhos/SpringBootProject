package com.sbproject.schedule.database;

import com.sbproject.schedule.models.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnExpression("${custom.use-test-database:false}==true")
public class TestDatabase {

    /**
     * primitive db for specialties
     * @return
     */
    @Bean
    public Map<Long, Specialty> specialties(){
        Map<Long, Specialty> s = new HashMap<Long, Specialty>();

        return s;
    }

    /**
     * primitive db for teachers
     * @return
     */
    @Bean
    public Map<Long, Teacher> teachers(){
        Map<Long, Teacher> t = new HashMap<Long, Teacher>();

        return t;
    }

    /**
     * primitive db for subjects
     * @return
     */
    @Bean
    public Map<Long, Subject> subjects(){
        Map<Long, Subject> s = new HashMap<Long, Subject>();
        return s;
    }

    /**
     * primitive db for users
     * @return
     */
    @Bean
    public Map<String, User> users() {
        Map<String, User> s = new HashMap<String, User>();
        return s;
    }


    @Bean
    public Map<Long, Lesson> lessons(){
        Map<Long, Lesson> l = new HashMap<Long, Lesson>();
        return l;
    }

}
