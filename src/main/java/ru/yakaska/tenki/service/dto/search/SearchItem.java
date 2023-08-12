package ru.yakaska.tenki.service.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchItem {

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;

}