package ru.yakaska.tenki.service;

import ru.yakaska.tenki.dto.location.*;

import java.util.List;

public interface LocationService {

    List<LocationDto> getAllLocations();

    LocationDto getLocationById(Long locationId);

    List<LocationDto> searchLocation(String locationName);

    LocationDto addLocation(LocationDto location);

    void deleteLocationById(Long locationId);

}
