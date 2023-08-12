package ru.yakaska.tenki.service;

import ru.yakaska.tenki.controller.location.dto.*;
import ru.yakaska.tenki.entity.*;

import java.util.*;

public interface LocationService {

    List<LocationDto> getAllLocations(User user);

    LocationDto getLocationById(Long locationId, User user);

    List<LocationDto> searchLocation(String locationName);

    LocationDto addLocation(LocationDto locationDto, User user);

    void deleteLocationById(Long locationId, User user);

}
