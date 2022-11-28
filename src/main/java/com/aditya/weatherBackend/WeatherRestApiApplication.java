package com.aditya.weatherBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootApplication
@EnableCaching
public class WeatherRestApiApplication {
	
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		SpringApplication.run(WeatherRestApiApplication.class, args);
		
	}
	

}
