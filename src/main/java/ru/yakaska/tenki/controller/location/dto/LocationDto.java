package ru.yakaska.tenki.controller.location.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    @JsonProperty("id")
    @NotNull
    // TODO: 11.08.2023 maybe should be replaced with null
    private Long id;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("country")
    @NotNull
    private String country;

    @JsonProperty("state")
    @JsonInclude(Include.NON_NULL)
    private String state;

    @JsonProperty("desc")
    @NotNull
    private String description;

    @JsonProperty("temp")
    @NotNull
    private Double temperature;

    @JsonProperty("lat")
    @NotNull
    private Double latitude;

    @JsonProperty("lon")
    @NotNull
    private Double longitude;

}
