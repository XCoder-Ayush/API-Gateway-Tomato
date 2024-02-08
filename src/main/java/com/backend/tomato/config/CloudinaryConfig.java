package com.backend.tomato.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getCloudinary(){
        Map config=new HashMap();
        config.put("cloud_name","dgfljm0r3");
        config.put("api_key","384727883763165");
        config.put("api_secret","6MEYBzY5q8SOXlYzSs4DHuyxvkQ");
        config.put("secure",true);
        return new Cloudinary(config);
    }

}
