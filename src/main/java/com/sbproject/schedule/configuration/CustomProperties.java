package com.sbproject.schedule.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private String adminCode;
    private String userCode;
    private boolean useTestDatabase;

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public boolean isUseTestDatabase() {
        return useTestDatabase;
    }

    public void setUseTestDatabase(boolean useTestDatabase) {
        this.useTestDatabase = useTestDatabase;
    }
}
