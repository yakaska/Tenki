package ru.yakaska.tenki.payload.location.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Main{

	@JsonProperty("temp")
	private Double temperature;

	@JsonProperty("feels_like")
	private Double feelsLike;

	@JsonProperty("humidity")
	private int humidity;

	@JsonProperty("pressure")
	private int pressure;


}