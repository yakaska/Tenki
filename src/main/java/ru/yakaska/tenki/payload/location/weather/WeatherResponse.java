package ru.yakaska.tenki.payload.location.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class WeatherResponse {

	@JsonProperty("main")
	private Main main;

	@JsonProperty("weather")
	private List<Weather> weather;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("coord")
	private Coordinate coordinate;

}