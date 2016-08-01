package com.gmail.at.ivanehreshi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@ImportResource("classpath:/context.xml") // xml file for old style configuration
public class AppConfig implements InitializingBean {

    // It's is a must to set up data source bean(or beans)
    @Bean
    public DataSource dataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/social_network");
        ds.setUser("ivaneh");
        ds.setPassword("password");
        return ds;
    }

    @Bean
    public UserSqlQuery_SqlQuery userSqlQuery_sqlQuery(DataSource dataSource) {
        return new UserSqlQuery_SqlQuery(dataSource);
    }

    @Bean
    public UserSqlQuery_MappingSqlQuery userSqlQuery_mappingSqlQuery(DataSource dataSource) {
        return new UserSqlQuery_MappingSqlQuery(dataSource);
    }

    // Now we will use NamedParameterJdbcTempalte
    @Bean
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AppConfig initialized");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserSqlQuery_SqlQuery userSqlQuery_sqlQuery =
                (UserSqlQuery_SqlQuery) ctx.getBean("userSqlQuery_sqlQuery");
        UserSqlQuery_MappingSqlQuery userSqlQuery_mappingSqlQuery =
                (UserSqlQuery_MappingSqlQuery) ctx.getBean("userSqlQuery_mappingSqlQuery");

        User user1 = userSqlQuery_sqlQuery.findObject(23);
        User user2 = userSqlQuery_mappingSqlQuery.findObject(23);
        System.out.println(user1);
        System.out.println(user2);
    }
}
