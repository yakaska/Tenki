package ru.yakaska.tenki.controller.location.dto.search;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
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