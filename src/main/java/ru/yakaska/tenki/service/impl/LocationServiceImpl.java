package ru.yakaska.tenki.service.impl;

import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.yakaska.tenki.api.*;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.exception.*;
import ru.yakaska.tenki.payload.location.*;
import ru.yakaska.tenki.payload.location.geo.*;
import ru.yakaska.tenki.payload.location.weather.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.*;

import java.util.*;

@Service
public class LocationServiceImpl implements LocationService {

    private final CurrentUserService currentUserService;
    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final OpenWeatherApi openWeatherApi;

    public LocationServiceImpl(CurrentUserService currentUserService, LocationRepository locationRepository, UserRepository userRepository, OpenWeatherApi openWeatherApi) {
        this.currentUserService = currentUserService;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.openWeatherApi = openWeatherApi;
    }

    @Override
    public List<LocationDto> getAllLocations() {

        User user = currentUserService.getCurrentUser();

        return user.getLocations().stream()
                .map(this::mapToDto)
                .sorted(Comparator.comparing(LocationDto::getId))
                .toList();
    }

    @Override
    public LocationDto getLocationById(Long locationId) {

        User user = currentUserService.getCurrentUser();

        Location location = user.getLocations()
                .stream()
                .filter(loc -> loc.getId().equals(locationId)).findFirst().orElseThrow(
                        () -> new TenkiException(HttpStatus.BAD_REQUEST, "No such location")
                );

        return mapToDto(location);
    }

    @Override
    public List<LocationDto> searchLocation(String locationName) {

        List<City> cities = openWeatherApi.search(locationName);

        return cities
                .stream()
                .map(city -> {
                    WeatherResponse weatherResponse = openWeatherApi.getWeather(city.getLatitude(), city.getLongitude());
                    LocationDto locationDto = new LocationDto();
                    locationDto.setId(weatherResponse.getId());
                    locationDto.setName(city.getName());
                    locationDto.setCountry(city.getCountry());
                    locationDto.setState(city.getState());
                    locationDto.setTemperature(weatherResponse.getMain().getTemperature());
                    locationDto.setWeatherDescription(weatherResponse.getWeather().get(0).getDescription());
                    return locationDto;
                })
                .toList();
    }

    @Override
    public LocationDto addLocation(LocationDto locationDto) {

        User user = currentUserService.getCurrentUser();

        Location location = locationRepository.findById(locationDto.getId()).orElseThrow(
                () -> new TenkiException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find location")
        );

        user.getLocations().add(location);
        userRepository.save(user);

        return mapToDto(location);
    }

    @Override
    @Transactional
    public void deleteLocationById(Long locationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + auth.getName() + " not found")
        );

        boolean isRemoved = user.getLocations().removeIf(location -> location.getId().equals(locationId));
        if (!isRemoved) {
            throw new TenkiException(HttpStatus.BAD_REQUEST, "Could not find location");
        }
    }


    private LocationDto mapToDto(Location location) {
        WeatherResponse weatherResponse = openWeatherApi.getWeather(location.getLatitude(), location.getLongitude());

        LocationDto resultDto = new LocationDto();
        resultDto.setId(location.getId());
        resultDto.setName(location.getName());
        resultDto.setCountry(location.getCountry());
        resultDto.setState(location.getState());
        resultDto.setTemperature(weatherResponse.getMain().getTemperature());
        resultDto.setWeatherDescription(weatherResponse.getWeather().get(0).getDescription());

        return resultDto;
    }

}
