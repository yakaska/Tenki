package ru.yakaska.tenki.service.impl;

import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.exception.*;
import ru.yakaska.tenki.payload.location.*;
import ru.yakaska.tenki.payload.location.response.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.*;

import java.net.*;
import java.util.*;

@Service
public class LocationServiceImpl implements LocationService {


    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    private static final String APP_ID = "12d10bef38432936b4fd94ee49e55a86";

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LocationDto> getAllLocations() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // TODO: 07.08.2023 extract to current user provider
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + auth.getName() + " not found")
        );

        return user.getLocations().stream().map(location -> {

            WeatherResponse weatherResponse = getWeather(location.getName());

            LocationDto locationDto = new LocationDto();
            locationDto.setId(location.getLocationId());
            locationDto.setName(location.getName());
            locationDto.setWeatherDescription(weatherResponse.getWeatherDescription());
            locationDto.setTemperature(weatherResponse.getTemperature());

            return locationDto;
        }).toList();
    }

    @Override
    public LocationDto searchLocation(String locationName) {
        WeatherResponse weatherResponse = getWeather(locationName);

        System.out.println(weatherResponse.getLatitude());

        Location location;
        if (!locationRepository.existsByName(locationName)) {
            location = new Location();
            location.setName(weatherResponse.getName());
            location.setLongitude(weatherResponse.getLongitude());
            location.setLatitude(weatherResponse.getLatitude());
            locationRepository.save(location);
        }

        location = locationRepository.findByName(locationName).orElseThrow(
                () -> new TenkiException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find location")
        );

        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getLocationId());
        locationDto.setName(location.getName());
        locationDto.setTemperature(weatherResponse.getTemperature());
        locationDto.setWeatherDescription(weatherResponse.getWeatherDescription());

        return locationDto;
    }

    @Override
    public LocationDto addLocation(LocationDto locationDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // TODO: 07.08.2023 extract to current user provider
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + auth.getName() + " not found")
        );

        Location location = locationRepository.findById(locationDto.getId()).orElseThrow(
                () -> new TenkiException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find location")
        );

        user.getLocations().add(location);
        userRepository.save(user);

        WeatherResponse weatherResponse = getWeather(location.getName());

        LocationDto resultDto = new LocationDto();
        resultDto.setId(location.getLocationId());
        resultDto.setName(location.getName());
        resultDto.setTemperature(weatherResponse.getTemperature());
        resultDto.setWeatherDescription(weatherResponse.getWeatherDescription());

        return resultDto;
    }

    private WeatherResponse getWeather(String locationName) {
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
