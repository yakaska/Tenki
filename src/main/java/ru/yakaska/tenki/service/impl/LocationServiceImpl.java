package ru.yakaska.tenki.service.impl;

import lombok.*;
import org.springframework.data.rest.webmvc.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.yakaska.tenki.api.*;
import ru.yakaska.tenki.controller.location.dto.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.*;
import ru.yakaska.tenki.service.dto.search.*;
import ru.yakaska.tenki.service.dto.weather.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final CurrentUserService currentUserService;

    private final UserRepository userRepository;

    private final OpenWeatherApi openWeatherApi;

    @Override
    public List<LocationDto> getAllLocations() {
        User user = currentUserService.getCurrentUser();
        return user.getLocations().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public LocationDto getLocationById(Long locationId) {
        User user = currentUserService.getCurrentUser();
        return user.getLocations()
                .stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .map(this::mapToDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No such location")
                );
    }

    @Override
    public List<LocationDto> searchLocation(String locationName) {

        List<SearchItem> searchResponse = openWeatherApi.search(locationName);

        return searchResponse.stream()
                .map(this::mapToDomain)
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public LocationDto addLocation(LocationDto location) {
        User user = currentUserService.getCurrentUser();
        user.getLocations().add(mapToDomain(location));
        userRepository.save(user);
        return location;
    }

    @Override
    @Transactional
    public void deleteLocationById(Long locationId) {
        User user = currentUserService.getCurrentUser();
        boolean isRemoved = user.getLocations().removeIf(location -> location.getId().equals(locationId));
        if (!isRemoved) {
            throw new ResourceNotFoundException("Could not find location");
        }
    }

    private LocationDto mapToDto(Location location) {
        WeatherResponse weatherResponse = openWeatherApi.fetchWeather(location.getLatitude(), location.getLongitude());
        return LocationDto.builder()
                .id(weatherResponse.getId())
                .country(location.getCountry())
                .state(location.getState())
                .name(location.getName())
                .description(weatherResponse.getWeather().get(0).getDescription())
                .temperature(weatherResponse.getMain().getTemp())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    private Location mapToDomain(LocationDto locationDto) {
        return Location.builder()
                .id(locationDto.getId())
                .country(locationDto.getCountry())
                .state(locationDto.getState())
                .name(locationDto.getName())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .build();
    }

    private Location mapToDomain(SearchItem item) {
        return Location.builder()
                .country(item.getCountry())
                .state(item.getState())
                .name(item.getName())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .build();
    }

}
