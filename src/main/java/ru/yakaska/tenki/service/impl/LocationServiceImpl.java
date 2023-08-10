package ru.yakaska.tenki.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yakaska.tenki.api.OpenWeatherApi;
import ru.yakaska.tenki.entity.Location;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.payload.location.LocationDto;
import ru.yakaska.tenki.payload.location.geo.City;
import ru.yakaska.tenki.payload.location.weather.WeatherResponse;
import ru.yakaska.tenki.repository.UserRepository;
import ru.yakaska.tenki.service.CurrentUserService;
import ru.yakaska.tenki.service.LocationService;

import java.util.Comparator;
import java.util.List;

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
                .sorted(Comparator.comparing(LocationDto::getId))
                .toList();
    }

    @Override
    public LocationDto getLocationById(Long locationId) {

        User user = currentUserService.getCurrentUser();

        Location location = user.getLocations()
                .stream()
                .filter(loc -> loc.getId().equals(locationId)).findFirst().orElseThrow(
                        () -> new ResourceNotFoundException("No such location")
                );

        return mapToDto(location);
    }

    @Override
    public List<LocationDto> searchLocation(String locationName) {

        List<City> cities = openWeatherApi.search(locationName);

        return cities
                .stream()
                .map(city -> {
                    Location location = new Location();
                    location.setName(city.getName());
                    location.setCountry(city.getCountry());
                    location.setState(city.getState());
                    location.setLatitude(city.getLatitude());
                    location.setLongitude(city.getLongitude());
                    return mapToDto(location);
                })
                .toList();
    }

    @Override
    public LocationDto addLocation(LocationDto locationDto) {
        User user = currentUserService.getCurrentUser();

        Location location = new Location();
        location.setId(locationDto.getId());
        location.setName(locationDto.getName());
        location.setCountry(locationDto.getCountry());
        location.setState(locationDto.getState());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());

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
            throw new ResourceNotFoundException("Could not find location");
        }
    }


    private LocationDto mapToDto(Location location) {
        WeatherResponse weatherResponse = openWeatherApi.getWeather(location.getLatitude(), location.getLongitude());

        LocationDto resultDto = new LocationDto();
        resultDto.setId(weatherResponse.getId());
        resultDto.setName(location.getName());
        resultDto.setCountry(location.getCountry());
        resultDto.setState(location.getState());
        resultDto.setTemperature(weatherResponse.getMain().getTemperature());
        resultDto.setDescription(weatherResponse.getWeather().get(0).getDescription());
        resultDto.setLatitude(weatherResponse.getCoordinate().getLatitude());
        resultDto.setLongitude(weatherResponse.getCoordinate().getLongitude());
        return resultDto;
    }

}
