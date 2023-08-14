package ru.yakaska.tenki.service.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class SearchResponse{

	@JsonProperty("locations")
	private List<SearchItem> locations;

}