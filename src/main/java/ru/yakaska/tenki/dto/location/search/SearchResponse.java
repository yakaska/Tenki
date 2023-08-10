package ru.yakaska.tenki.dto.location.search;

import lombok.*;

import java.util.List;

@Getter
public class SearchResponse{

	private List<SearchItem> locations;

}