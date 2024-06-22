package spring.boot.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import spring.boot.jwt.filter.JWTFilter;
import spring.boot.jwt.service.JWTService;

@Configuration
public class JWTConfig {

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter();
    }

    @Bean
    public JWTService jwtService() {
        return new JWTService();
    }
}
