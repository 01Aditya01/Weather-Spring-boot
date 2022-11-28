package com.aditya.weatherBackend.callAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.aditya.weatherBackend.hourlyData.Weatherdata;
import com.aditya.weatherBackend.repos.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GetWeatherData {

	@Autowired
	WeatherRepository repository;
	@Value("${developer.apikey}")
	private String apikey;

	
	public void createdb() throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		
		String cities[] = {"Delhi", "Paris", "London", "Tokyo"};
		
		repository.deleteAll();
		for (String city : cities) {
			
			String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q="+city+ "&appid=" +apikey; 
			
			ResponseEntity<String> hourly = restTemplate.getForEntity(
					apiUrl,
					String.class);
			String body = hourly.getBody();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode arr = mapper.readTree(body).path("list");
			
			
			for (JsonNode node : arr) {
				double temp = node.path("main").get("temp").asDouble();
				JsonNode weather = node.path("weather").path(0);
				String description = weather.get("description").asText();
				String icon = weather.get("icon").asText();
				String time = node.get("dt_txt").asText();
				
				Weatherdata data = new Weatherdata();
				data.setTemp(temp);
				data.setDescription(description);
				data.setIcon(icon);
				data.setTime(time);
				data.setCity(city);
				
				repository.save(data);
			}
		}

	}
}
