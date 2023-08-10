package ru.yakaska.tenki.service;

import ru.yakaska.tenki.entity.Location;
import ru.yakaska.tenki.payload.location.LocationDto;

import java.util.List;

public interface LocationService {

    List<Location> getAllLocations();

    Location getLocationById(Long locationId);

    List<Location> searchLocation(String locationName);

    Location addLocation(Location location);

    void deleteLocationById(Long locationId);

}
