package com.example.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 Using Maven model for project dependencies
 Use Spring WEB dependency since this will be a web application and not REST API. T
 There will be html responses using Thymeleaf. We can write HTML templates without having to create a front end using javascript and use on a browser
 Thymeleaf dependency gives you a templating library to render HTML
 Spring Boot DevTools for fast application restarts
 No database used, using GET requests to The ONE API (https://the-one-api.dev/documentation) and renders in a web format. Creating a UI skin for the data.
 Taking in the data, parsing it and hook it into the Spring lifecycle and present in a nice format.
 */

@SpringBootApplication
@EnableScheduling
public class OneApplication {

	//You start your Spring Boot application here
	public static void main(String[] args) {
		SpringApplication.run(OneApplication.class, args);
	}

}
