package ru.yakaska.tenki.service;

import ru.yakaska.tenki.payload.location.*;

import java.util.*;

public interface LocationService {

    List<LocationDto> getAllLocations();

    LocationDto searchLocation(String locationName);

    LocationDto addLocation(LocationDto locationDto);

}
