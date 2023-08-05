package ru.yakaska.tenki.dto;

import lombok.*;

import java.math.*;

@Getter
@Setter
@AllArgsConstructor
public class LocationDto {

    private String name;

    private String description;

    private Double temperature;

    private Double feelsLike;

    private Double windSpeed;

    private Double latitude;

    private Double longitude;

}
