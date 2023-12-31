package ru.yakaska.tenki.api.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse{

	@JsonProperty("visibility")
	private Long visibility;

	@JsonProperty("main")
	private Main main;

	@JsonProperty("clouds")
	private Clouds clouds;

	@JsonProperty("sys")
	private Sys sys;

	@JsonProperty("dt")
	private Long dt;

	@JsonProperty("coord")
	private Coord coord;

	@JsonProperty("weather")
	private List<WeatherItem> weather;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cod")
	private Long cod;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("base")
	private String base;

	@JsonProperty("wind")
	private Wind wind;


}