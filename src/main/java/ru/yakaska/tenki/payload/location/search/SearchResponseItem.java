package ru.yakaska.tenki.payload.location.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResponseItem{

	@JsonProperty("local_names")
	private LocalNames localNames;

	@JsonProperty("country")
	private String country;

	@JsonProperty("name")
	private String name;

	@JsonProperty("lon")
	private Object lon;

	@JsonProperty("state")
	private String state;

	@JsonProperty("lat")
	private Object lat;

	public LocalNames getLocalNames(){
		return localNames;
	}

	public String getCountry(){
		return country;
	}

	public String getName(){
		return name;
	}

	public Object getLon(){
		return lon;
	}

	public String getState(){
		return state;
	}

	public Object getLat(){
		return lat;
	}
}