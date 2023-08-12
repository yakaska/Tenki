package ru.yakaska.tenki.service.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Main {

    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("temp_min")
    private Object tempMin;

    @JsonProperty("grnd_level")
    private int grndLevel;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("sea_level")
    private int seaLevel;

    @JsonProperty("feels_like")
    private Object feelsLike;

    @JsonProperty("temp_max")
    private Object tempMax;

}