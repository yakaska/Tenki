package ru.yakaska.tenki.api;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import ru.yakaska.tenki.exception.*;
import ru.yakaska.tenki.payload.location.geo.*;
import ru.yakaska.tenki.payload.location.weather.*;

import java.net.*;
import java.util.*;

@Component
@Scope("singleton")
public class OpenWeatherApi {

    @Value("${api.host.baseurl}")
    private String baseUrl;

    @Value("${api.host.appid}")
    private String appId;

    private final RestTemplate restTemplate;

    public OpenWeatherApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(Double latitude, Double longitude) {
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + "data/2.5/weather")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("units", "metric")
                .queryParam("appid", appId)
                .build(false)
                .toUri();

        WeatherResponse weatherResponse = restTemplate.getForEntity(uri, WeatherResponse.class).getBody();

        if (weatherResponse == null) {
            throw new IllegalStateException("Weather for that location is not available");
        }
        return weatherResponse;
    }

    public List<City> search(String cityName) {
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + "geo/1.0/direct")
                .queryParam("q", cityName)
                .queryParam("limit", 10)
                .queryParam("units", "metric")
                .queryParam("appid", appId)
                .build(false)
                .toUri();

        ParameterizedTypeReference<List<City>> type = new ParameterizedTypeReference<>() {
        };

        List<City> cities = restTemplate.exchange(uri, HttpMethod.GET, null, type).getBody();

        if (cities == null) {
            throw new TenkiException(HttpStatus.BAD_REQUEST, "No such location");
        }

        return cities;
    }


}
