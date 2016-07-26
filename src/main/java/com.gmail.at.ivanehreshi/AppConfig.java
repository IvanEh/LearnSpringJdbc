package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/context.xml")
public class AppConfig implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AppConfig initialized");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
