package ru.yakaska.tenki.service.impl;

import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.yakaska.tenki.api.OpenWeatherApi;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.exception.*;
import ru.yakaska.tenki.payload.location.*;
import ru.yakaska.tenki.payload.location.response.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.*;

import java.util.*;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final OpenWeatherApi openWeatherApi;

    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository, OpenWeatherApi openWeatherApi) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.openWeatherApi = openWeatherApi;
    }

    @Override
    public List<LocationDto> getAllLocations() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // TODO: 07.08.2023 extract to current user provider
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + auth.getName() + " not found")
        );

        return user.getLocations().stream()
                .map(location -> {

                    WeatherResponse weatherResponse = openWeatherApi.getWeather(location.getName());

                    LocationDto locationDto = new LocationDto();
                    locationDto.setId(location.getLocationId());
                    locationDto.setName(location.getName());
                    locationDto.setWeatherDescription(weatherResponse.getWeatherDescription());
                    locationDto.setTemperature(weatherResponse.getTemperature());

                    return locationDto;
                })
                .sorted(Comparator.comparing(LocationDto::getId))
                .toList();
    }

    @Override
    public LocationDto getLocationById(Long locationId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + auth.getName() + " not found")
        );

        Location location = locationRepository.findByLocationIdAndUsersUserId(
                locationId, user.getUserId()
        ).orElseThrow(() -> new TenkiException(HttpStatus.NOT_FOUND, "No such added location"));


        return mapToDto(location);
    }

    @Override
    public LocationDto searchLocation(String locationName) {
        WeatherResponse weatherResponse = openWeatherApi.getWeather(locationName);

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

        return mapToDto(location);
    }

    @Override
    @Transactional
    public void deleteLocationById(Long locationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(auth.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + auth.getName() + " not found")
        );

        boolean isRemoved = user.getLocations().removeIf(location -> location.getLocationId().equals(locationId));
        if (!isRemoved) {
            throw new TenkiException(HttpStatus.BAD_REQUEST, "Could not find location");
        }
    }


    private LocationDto mapToDto(Location location) {
        WeatherResponse weatherResponse = openWeatherApi.getWeather(location.getName());

        LocationDto resultDto = new LocationDto();
        resultDto.setId(location.getLocationId());
        resultDto.setName(location.getName());
        resultDto.setTemperature(weatherResponse.getTemperature());
        resultDto.setWeatherDescription(weatherResponse.getWeatherDescription());

        return resultDto;
    }

}
