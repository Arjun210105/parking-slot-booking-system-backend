package com.parking.parking_slot_booking_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //tells this class has bean definitions
public class ApplicationConfig {
    @Bean //when ever someone asked for password encode use this method
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
