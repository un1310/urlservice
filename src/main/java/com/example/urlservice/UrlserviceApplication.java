package com.example.urlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class UrlserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlserviceApplication.class, args);
	}

}
