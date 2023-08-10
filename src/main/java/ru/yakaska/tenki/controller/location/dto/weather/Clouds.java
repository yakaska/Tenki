package ru.yakaska.tenki.controller.location.dto.weather;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
public class Clouds{

	@JsonProperty("all")
	private int all;

}