package ru.yakaska.tenki.controller.location.dto.weather;

import com.fasterxml.jackson.annotation.*;

public class Wind{

	@JsonProperty("deg")
	private int deg;

	@JsonProperty("speed")
	private Object speed;

	@JsonProperty("gust")
	private Object gust;

	public int getDeg(){
		return deg;
	}

	public Object getSpeed(){
		return speed;
	}

	public Object getGust(){
		return gust;
	}
}