package ru.yakaska.tenki.api;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.data.rest.webmvc.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import ru.yakaska.tenki.controller.location.dto.search.*;
import ru.yakaska.tenki.controller.location.dto.weather.*;

import java.net.*;
import java.util.*;

@Component
@Scope("singleton")
// TODO: 10.08.2023 use feign
public class OpenWeatherApi {

    @Value("${api.host.baseurl}")
    private String baseUrl;

    @Value("${api.host.appid}")
    private String appId;

    private final RestTemplate restTemplate;

    public OpenWeatherApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse fetchWeather(Double latitude, Double longitude) {
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

    public List<SearchItem> search(String cityName) {
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + "geo/1.0/direct")
                .queryParam("q", cityName)
                .queryParam("limit", 10)
                .queryParam("units", "metric")
                .queryParam("appid", appId)
                .build(false)
                .toUri();

        List<SearchItem> searchResponse = restTemplate
                .exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<SearchItem>>() {
                        }
                ).getBody();

        if (searchResponse == null) {
            throw new ResourceNotFoundException("Could not find such location");
        }

        return searchResponse;
    }

}
