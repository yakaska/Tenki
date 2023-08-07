package ru.yakaska.tenki.controller;

import org.springframework.http.*;
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

    @GetMapping("/{id}")
    public LocationDto getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/search")
    public List<LocationDto> searchLocation(@RequestParam("q") String locationName) {
        return locationService.searchLocation(locationName);
    }

    @PostMapping("/")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto) {
        return new ResponseEntity<>(locationService.addLocation(locationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeLocation(@PathVariable(name = "id") Long id) {
        locationService.deleteLocationById(id);

        return new ResponseEntity<>("Location deleted successfully.", HttpStatus.OK);
    }

}
