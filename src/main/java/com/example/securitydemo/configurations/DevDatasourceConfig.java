package com.example.securitydemo.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Configuration
//@Profile("dev")
//@Slf4j
//@ImportResource("classpath:springProfiles-config.xml")
public class DevDatasourceConfig {

//    @Value("#{devDatasourceConfig.appName}")
    private String appName;




    public String getAppName() {
        return appName;
    }


    public void setAppName(String appName) {
        this.appName = appName;
    }
}
