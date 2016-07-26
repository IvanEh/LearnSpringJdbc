package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
        implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AppConfig initialized");
    }
}
