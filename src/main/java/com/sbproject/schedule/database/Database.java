package com.sbproject.schedule.database;

import com.sbproject.schedule.models.Specialty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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




}
