package ru.yakaska.tenki.service;

import ru.yakaska.tenki.controller.location.dto.LocationDto;
import ru.yakaska.tenki.entity.User;

import java.util.List;

public interface LocationService {

    List<LocationDto> getAllLocations(User user);

    LocationDto getLocationById(Long locationId, User user);

    List<LocationDto> searchLocation(String locationName);

    LocationDto addLocation(LocationDto locationDto, User user);

    void deleteLocationById(Long locationId, User user);

}
