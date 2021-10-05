package com.sbproject.schedule.configuration;

import com.demo.customstarter.utils.Utils;
import com.sbproject.schedule.utils.NewUtilsImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    @ConditionalOnProperty(prefix = "custom", name = "use-starter-utils", havingValue = "false", matchIfMissing = true)
    public Utils getUtils(){
        return new NewUtilsImpl();
    }


}
