package ru.yakaska.tenki.service;

import ru.yakaska.tenki.payload.location.*;

import java.util.*;

public interface LocationService {

    List<LocationDto> getAllLocations();

    LocationDto getLocationById(Long locationId);

    LocationDto searchLocation(String locationName);

    LocationDto addLocation(LocationDto locationDto);

    void deleteLocationById(Long locationId);

}
