package ru.yakaska.tenki.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LocationDto {

    private String name;

    private String description;

    private Double temperature;

    private Double feelsLike;

    private Double windSpeed;

}
