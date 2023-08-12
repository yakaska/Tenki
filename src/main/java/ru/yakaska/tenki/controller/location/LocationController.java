package ru.yakaska.tenki.controller.location;

import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.controller.location.dto.*;
import ru.yakaska.tenki.service.*;

import java.util.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    private final CurrentUserService currentUserService;


    public LocationController(LocationService locationService, CurrentUserService currentUserService) {
        this.locationService = locationService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public List<LocationDto> getAll() {
        return locationService.getAllLocations(currentUserService.getCurrentUser());
    }

    @GetMapping("/{id}")
    public LocationDto getById(@PathVariable Long id) {
        return locationService.getLocationById(id, currentUserService.getCurrentUser());
    }

    @GetMapping("search")
    public List<LocationDto> search(@RequestParam("q") String locationName) {
        return locationService.searchLocation(locationName);
    }

    @PostMapping
    public ResponseEntity<LocationDto> add(@RequestBody @Valid LocationDto location) {
        return new ResponseEntity<>(locationService.addLocation(location, currentUserService.getCurrentUser()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        locationService.deleteLocationById(id, currentUserService.getCurrentUser());
        return ResponseEntity.noContent().build();
    }

}
