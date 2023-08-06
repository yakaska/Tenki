package ru.yakaska.tenki.controller;

import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.payload.location.*;
import ru.yakaska.tenki.service.*;

import java.util.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/")
    public List<LocationDto> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/search")
    public LocationDto searchLocation(@RequestParam("q") String locationName) {
        return locationService.searchLocation(locationName);
    }

    @PostMapping("/")
    public LocationDto addLocation(@RequestBody LocationDto locationDto) {
        return locationService.addLocation(locationDto);
    }

}
