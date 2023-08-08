package ru.yakaska.tenki.payload.location.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Weather {

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("description")
	private String description;

	@JsonProperty("main")
	private String weather;

	@JsonProperty("id")
	private int id;
}