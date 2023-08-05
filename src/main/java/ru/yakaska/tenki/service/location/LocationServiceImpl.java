package ru.yakaska.tenki.service.location;

import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.security.*;
import ru.yakaska.tenki.service.location.model.*;

import java.net.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    private static final String API_KEY = "12d10bef38432936b4fd94ee49e55a86";

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final AuthFacade authFacade;

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

        WeatherResponse weatherResponse = response.getBody();

        if (!response.getStatusCode().equals(HttpStatus.OK) || weatherResponse == null)
            throw new IllegalStateException("Something went wrong");


        return new LocationDto(
                weatherResponse.getName(),
                weatherResponse.getWeather().get(0).getDescription(),
                weatherResponse.getMain().getTemp(),
                weatherResponse.getMain().getFeelsLike(),
                weatherResponse.getWind().getSpeed(),
                weatherResponse.getCoord().getLat(),
                weatherResponse.getCoord().getLon()
        );
    }

    @Override
    public List<LocationDto> findAll() {
        String currentUsername = authFacade.getAuthentication().getName();

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
                    weatherResponse.getWind().getSpeed(),
                    weatherResponse.getCoord().getLat(),
                    weatherResponse.getCoord().getLon()
            ));
        }

        return result;
    }

    @Override
    public LocationDto add(String locationName) {

        LocationDto locationDto = search(locationName);

        Location location = Location.builder()
                .name(locationDto.getName())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .build();

        location = locationRepository.save(location);

        String currentUsername = authFacade.getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByUsername(currentUsername);

        if (currentUser.isEmpty())
            throw new IllegalStateException("Something went wrong");

        currentUser.get().getLocations().add(location);

        userRepository.save(currentUser.get());

        return locationDto;
    }

    @Override
    public LocationDto delete(String name) {
        return null;
    }
}
