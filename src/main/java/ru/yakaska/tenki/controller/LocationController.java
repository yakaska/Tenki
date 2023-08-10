package ru.yakaska.tenki.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.payload.location.LocationDto;
import ru.yakaska.tenki.service.LocationService;

import java.util.List;

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
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto location) {
        return new ResponseEntity<>(locationService.addLocation(location), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeLocation(@PathVariable(name = "id") Long id) {
        locationService.deleteLocationById(id);

        return new ResponseEntity<>("Location deleted successfully.", HttpStatus.OK);
    }

}
