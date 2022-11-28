package com.aditya.weatherBackend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aditya.weatherBackend.hourlyData.Weatherdata;

@Repository
public interface WeatherRepository extends JpaRepository<Weatherdata, Integer> {
	
	List<Weatherdata> findByCity(String city);
	
	@Transactional
	Long deleteByCity(String city);
	}


