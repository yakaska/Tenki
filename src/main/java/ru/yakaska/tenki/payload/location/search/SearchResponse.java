package ru.yakaska.tenki.payload.location.search;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResponse{

	private List<SearchResponseItem> locations;

	public List<SearchResponseItem> getLocations(){
		return locations;
	}
}