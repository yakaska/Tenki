package ru.yakaska.tenki.payload.location.weather;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

}