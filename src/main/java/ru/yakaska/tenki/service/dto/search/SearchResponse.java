package ru.yakaska.tenki.service.dto.search;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@Getter
public class SearchResponse{

	@JsonProperty("locations")
	private List<SearchItem> locations;

}