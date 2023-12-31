package ru.yakaska.tenki.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yakaska.tenki.api.OpenWeatherApi;
import ru.yakaska.tenki.api.dto.search.SearchItem;
import ru.yakaska.tenki.api.dto.weather.WeatherResponse;
import ru.yakaska.tenki.controller.location.dto.LocationDto;
import ru.yakaska.tenki.entity.Location;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.repository.UserRepository;
import ru.yakaska.tenki.service.LocationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final UserRepository userRepository;

    private final OpenWeatherApi openWeatherApi;

    @Override
    public List<LocationDto> getAllLocations(User user) {
        return user.getLocations().stream()
                .map(this::fillDto)
                .toList();
    }

    @Override
    public LocationDto getLocationById(Long locationId, User user) {
        return user.getLocations()
                .stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .map(this::fillDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No such location")
                );
    }

    @Override
    public List<LocationDto> searchLocation(String locationName) {

        List<SearchItem> searchResponse = openWeatherApi.search(locationName);

        if (searchResponse.isEmpty()) {
            throw new ResourceNotFoundException("Could not find location");
        }

        return searchResponse.stream()
                .map(this::mapToDomain)
                .map(this::fillDto)
                .toList();
    }

    @Override
    public LocationDto addLocation(LocationDto location, User user) {
        user.getLocations().add(mapToDomain(location));
        userRepository.save(user);
        return location;
    }

    @Override
    @Transactional
    public void deleteLocationById(Long locationId, User user) {
        boolean isRemoved = user.getLocations().removeIf(location -> location.getId().equals(locationId));
        if (!isRemoved) {
            throw new ResourceNotFoundException("Could not find location");
        }
    }

    private LocationDto fillDto(Location location) {
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
