package ru.yakaska.tenki.service.location;

import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.location.model.*;

import java.math.*;
import java.net.*;
import java.util.*;

@Service
@RequiredArgsConstructor
// TODO: 04.08.2023 put request building in separate service i.e. retrofit or use object to DTO mapper
public class LocationServiceImpl implements LocationService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    private static final String API_KEY = "12d10bef38432936b4fd94ee49e55a86";

    private final UserRepository userRepository;

    @Override
    public LocationDto search(String locationName) {

        RestTemplate restTemplate = new RestTemplate();

        String urlEncoded = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", locationName)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .encode()
                .toUriString();

        URI uri = URI.create(urlEncoded);

        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(uri, WeatherResponse.class);
        System.out.println(response);

        WeatherResponse weatherResponse = response.getBody();

        if (!response.getStatusCode().equals(HttpStatus.OK) || weatherResponse == null)
            throw new IllegalStateException("Something went wrong");


        return new LocationDto(
                weatherResponse.getName(),
                weatherResponse.getWeather().get(0).getDescription(),
                weatherResponse.getMain().getTemp(),
                weatherResponse.getMain().getFeelsLike(),
                weatherResponse.getWind().getSpeed()
        );
    }

    @Override
    public List<LocationDto> findAll() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> currentUser = userRepository.findByUsername(currentUsername);

        if (currentUser.isEmpty())
            return Collections.emptyList();

        List<LocationDto> result = new ArrayList<>();
        for (Location location : currentUser.get().getLocations()) {
            String urlEncoded = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("q", location.getName())
                    .queryParam("appid", API_KEY)
                    .queryParam("units", "metric")
                    .encode()
                    .toUriString();

            URI uri = URI.create(urlEncoded);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(uri, WeatherResponse.class);
            WeatherResponse weatherResponse = response.getBody();
            if (!response.getStatusCode().equals(HttpStatus.OK) || weatherResponse == null) {
                throw new IllegalStateException("Something went wrong");
            }
            result.add(new LocationDto(
                    weatherResponse.getName(),
                    weatherResponse.getWeather().get(0).getDescription(),
                    weatherResponse.getMain().getTemp(),
                    weatherResponse.getMain().getFeelsLike(),
                    weatherResponse.getWind().getSpeed()
            ));
        }

        return result;
    }

    @Override
    public LocationDto add(BigDecimal latitude, BigDecimal longitude) {
        return null;
    }

    @Override
    public LocationDto delete(String name) {
        return null;
    }
}
