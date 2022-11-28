package com.aditya.weatherBackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aditya.weatherBackend.hourlyData.Weatherdata;
import com.aditya.weatherBackend.repos.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class WeatherRestController {

	@Autowired
	WeatherRepository repository;
	@Value("${developer.apikey}")
	private String apikey;

	@RequestMapping(value = "/{city}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	@Cacheable(value = "weather-cache")
	public List<Weatherdata> getWeatherData(@PathVariable("city") String city) {
		return repository.findByCity(city);
	}

	@RequestMapping(value = "createdb/{city}")
	@CacheEvict(value = "weather-cache")
	public void fetchWeatherData(@PathVariable("city") String city)
			throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();

		repository.deleteByCity(city);

		String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apikey;

		ResponseEntity<String> hourly = restTemplate.getForEntity(apiUrl, String.class);
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
