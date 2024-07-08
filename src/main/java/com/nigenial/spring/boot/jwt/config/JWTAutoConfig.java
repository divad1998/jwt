package com.nigenial.spring.boot.jwt.config;

import com.nigenial.spring.boot.jwt.filter.JWTFilter;
import com.nigenial.spring.boot.jwt.service.JWTService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
//import spring.boot.jwt.filter.JWTFilter;
//import spring.boot.jwt.service.JWTService;

@AutoConfiguration
public class JWTAutoConfig {

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter();
    }

    @Bean
    public JWTService jwtService() {
        return new JWTService();
    }
}
