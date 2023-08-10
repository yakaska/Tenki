package ru.yakaska.tenki.payload.location.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

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