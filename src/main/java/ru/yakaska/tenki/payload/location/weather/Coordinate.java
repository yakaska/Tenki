package ru.yakaska.tenki.payload.location.weather;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
public class Coordinate {

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;

}
