package ru.yakaska.tenki.payload.location;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;
import lombok.*;

@Data
public class LocationDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    @JsonInclude(Include.NON_NULL)
    private String state;

    @JsonProperty("desc")
    private String weatherDescription;

    @JsonProperty("temp")
    private Double temperature;

}
