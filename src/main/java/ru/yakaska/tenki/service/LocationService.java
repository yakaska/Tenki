package ru.yakaska.tenki.service;

import ru.yakaska.tenki.payload.location.LocationDto;

import java.util.List;

public interface LocationService {

    List<LocationDto> getAllLocations();

    LocationDto getLocationById(Long locationId);

    List<LocationDto> searchLocation(String locationName);

    LocationDto addLocation(LocationDto locationDto);

    void deleteLocationById(Long locationId);

}
