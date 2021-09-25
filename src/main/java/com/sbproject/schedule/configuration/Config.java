package com.sbproject.schedule.configuration;

import com.sbproject.schedule.utils.Utils;
import com.sbproject.schedule.utils.UtilsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Utils getUtils(){
        return new UtilsImpl();
    }


}
