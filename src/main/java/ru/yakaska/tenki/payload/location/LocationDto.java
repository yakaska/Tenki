package ru.yakaska.tenki.payload.location;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
public class LocationDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("desc")
    private String weatherDescription;

    @JsonProperty("temp")
    private Double temperature;

}
