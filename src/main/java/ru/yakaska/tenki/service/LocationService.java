package ru.yakaska.tenki.service;

import ru.yakaska.tenki.controller.location.dto.*;

import java.util.*;

public interface LocationService {

    List<LocationDto> getAllLocations();

    LocationDto getLocationById(Long locationId);

    List<LocationDto> searchLocation(String locationName);

    LocationDto addLocation(LocationDto location);

    void deleteLocationById(Long locationId);

}
