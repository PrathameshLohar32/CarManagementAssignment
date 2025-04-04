package com.CarManagementApplication.CarManagementApplication.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {

    @Value("${spring.app.API_KEY}")
    private String API_KEY;
    @Value("${spring.app.API_SECRET}")
    private String API_SECRET;
    @Value("${spring.app.CLOUD_NAME}")
    private String CLOUD_NAME;


    @Bean
    public Cloudinary getCloudinary(){

        Map config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        config.put("secure",true);


        return new Cloudinary(config);
    }
}
