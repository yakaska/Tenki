package ru.yakaska.tenki.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yakaska.tenki.dto.location.search.SearchResponse;
import ru.yakaska.tenki.dto.location.weather.WeatherResponse;

import java.net.URI;

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

    public SearchResponse search(String cityName) {
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + "geo/1.0/direct")
                .queryParam("q", cityName)
                .queryParam("limit", 10)
                .queryParam("units", "metric")
                .queryParam("appid", appId)
                .build(false)
                .toUri();

        SearchResponse searchResponse = restTemplate.getForEntity(uri, SearchResponse.class).getBody();

        if (searchResponse == null) {
            throw new ResourceNotFoundException("Could not find such location");
        }

        return searchResponse;
    }

}
