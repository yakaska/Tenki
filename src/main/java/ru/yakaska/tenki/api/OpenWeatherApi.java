package ru.yakaska.tenki.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yakaska.tenki.exception.TenkiException;
import ru.yakaska.tenki.payload.location.geo.City;
import ru.yakaska.tenki.payload.location.weather.WeatherResponse;

import java.net.URI;
import java.util.List;

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
