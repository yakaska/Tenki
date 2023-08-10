package ru.yakaska.tenki.dto.location.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds{

	@JsonProperty("all")
	private int all;

	public int getAll(){
		return all;
	}
}