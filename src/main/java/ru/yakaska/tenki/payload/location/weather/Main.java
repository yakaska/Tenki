package ru.yakaska.tenki.payload.location.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Main{

	@JsonProperty("temp")
	private Object temp;

	@JsonProperty("temp_min")
	private Object tempMin;

	@JsonProperty("grnd_level")
	private int grndLevel;

	@JsonProperty("humidity")
	private int humidity;

	@JsonProperty("pressure")
	private int pressure;

	@JsonProperty("sea_level")
	private int seaLevel;

	@JsonProperty("feels_like")
	private Object feelsLike;

	@JsonProperty("temp_max")
	private Object tempMax;

	public Object getTemp(){
		return temp;
	}

	public Object getTempMin(){
		return tempMin;
	}

	public int getGrndLevel(){
		return grndLevel;
	}

	public int getHumidity(){
		return humidity;
	}

	public int getPressure(){
		return pressure;
	}

	public int getSeaLevel(){
		return seaLevel;
	}

	public Object getFeelsLike(){
		return feelsLike;
	}

	public Object getTempMax(){
		return tempMax;
	}
}