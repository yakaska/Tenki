package ru.yakaska.tenki.api.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse{

	@JsonProperty("locations")
	private List<SearchItem> locations;

}