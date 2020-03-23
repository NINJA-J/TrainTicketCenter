package com.icss.Config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Properties;

@Configuration
public class MybatisConfig {

    public MybatisConfig() throws IOException {
        System.out.println("Service Config Initiated...");
    }

    @Bean(name = "dbProperties")
    public Properties getDBProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/Properties/db.properties"));
        return properties;
    }

    @Bean
    public SqlSessionFactory getSqlSessionFactory() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("Config/mybatis-config.xml");
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
