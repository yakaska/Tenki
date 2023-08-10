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
class LocationServiceImpl implements LocationService {

    private final CurrentUserService currentUserService;

    private final UserRepository userRepository;

    private final OpenWeatherApi openWeatherApi;

    @Override
    public List<Location> getAllLocations() {

        User user = currentUserService.getCurrentUser();

        return user.getLocations().stream()
                .sorted(Comparator.comparing(Location::getId))
                .toList();
    }

    @Override
    public Location getLocationById(Long locationId) {
        User user = currentUserService.getCurrentUser();
        return user.getLocations()
                .stream()
                .filter(loc -> loc.getId().equals(locationId)).findFirst().orElseThrow(
                        () -> new ResourceNotFoundException("No such location")
                );
    }

    @Override
    public List<Location> searchLocation(String locationName) {

        WeatherResponse weatherResponse = openWeatherApi.search(locationName);

        return cities
                .stream()
                .map(city -> Location.builder()
                        .name(city.getName())
                        .country(city.getCountry())
                        .state(city.getState())
                        .latitude(city.getLatitude())
                        .longitude(city.getLongitude())
                        .build())
                .toList();
    }

    @Override
    public Location addLocation(Location location) {
        User user = currentUserService.getCurrentUser();
        user.getLocations().add(location);
        userRepository.save(user);
        return location;
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

}
