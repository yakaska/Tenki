package ru.yakaska.tenki.controller.location.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String description;

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;

}
