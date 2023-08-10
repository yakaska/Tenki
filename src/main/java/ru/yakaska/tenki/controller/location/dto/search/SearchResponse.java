package ru.yakaska.tenki.controller.location.dto.search;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@Getter
public class SearchResponse{

	@JsonProperty("locations")
	private List<SearchItem> locations;

}