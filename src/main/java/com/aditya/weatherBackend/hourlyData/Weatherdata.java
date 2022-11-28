package com.aditya.weatherBackend.hourlyData;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
@IdClass(WeatherdataId.class)
public class Weatherdata implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String city;
	@Id
	private String time;
	private double temp;
	private String description;
	private String icon;


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	


}
