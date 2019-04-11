package com.skilldistillery.coderdojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CoderDojoApplication extends SpringBootServletInitializer{
    @Bean
    public PasswordEncoder configurePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoderDojoApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CoderDojoApplication.class, args);
	}

}
