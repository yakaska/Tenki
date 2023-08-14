package ru.yakaska.tenki.service.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class Clouds {

    @JsonProperty("all")
    private int all;

}