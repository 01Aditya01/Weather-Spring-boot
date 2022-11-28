package com.aditya.weatherBackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherRestApiApplicationTests {
	
	@Value("${weatherrestapi.local.url}")
	private String baseUrl;

	@Test
	void getDelhiWeather() {
		System.out.println(baseUrl);
		System.out.println("Inside test method");
		RestTemplate resttemplate = new RestTemplate();
		 ResponseEntity<String> response = resttemplate.getForEntity(baseUrl, String.class);
		 System.out.println(response);
		 assertNotNull(response);
		 
	}

}
