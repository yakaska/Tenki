package ru.yakaska.tenki.payload.location.response;

import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;

@Data
@JsonDeserialize(using = WeatherResponseDeserializer.class)
public class WeatherResponse {

    private String name;

    private String weatherDescription;

    private Double temperature;

    private Double latitude;

    private Double longitude;

}
