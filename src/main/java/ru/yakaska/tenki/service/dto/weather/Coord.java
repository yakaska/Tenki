package ru.yakaska.tenki.service.dto.weather;

import com.fasterxml.jackson.annotation.*;

public class Coord{

	@JsonProperty("lon")
	private Object lon;

	@JsonProperty("lat")
	private Object lat;

	public Object getLon(){
		return lon;
	}

	public Object getLat(){
		return lat;
	}
}