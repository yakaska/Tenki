package ru.yakaska.tenki.service.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Wind{

	@JsonProperty("deg")
	private int deg;

	@JsonProperty("speed")
	private Object speed;

	@JsonProperty("gust")
	private Object gust;

}