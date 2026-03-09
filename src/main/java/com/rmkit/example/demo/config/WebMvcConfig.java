package com.rmkit.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map: http://host/uploads/staff/<filename> -> local folder ./uploads/staff/
        registry.addResourceHandler("/uploads/staff/**")
                .addResourceLocations("file:uploads/staff/");
    }
}