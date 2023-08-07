package ru.yakaska.tenki.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yakaska.tenki.payload.location.response.WeatherResponse;

import java.net.URI;

@Component
@Scope("singleton")
public class OpenWeatherApi {

    @Value("${api.host.baseurl}")
    private String BASE_URL;

    @Value("${api.host.appid}")
    private String APP_ID;

    private final RestTemplate restTemplate;

    public OpenWeatherApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String locationName) {
        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("q", locationName)
                .queryParam("units", "metric")
                .queryParam("appid", APP_ID)
                .build(false)
                .toUri();

        WeatherResponse weatherResponse = restTemplate.getForEntity(uri, WeatherResponse.class).getBody();

        if (weatherResponse == null) {
            throw new IllegalStateException("Weather for that location is not available");
        }
        return weatherResponse;
    }

}
