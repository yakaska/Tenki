package ru.yakaska.tenki.controller.location;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakaska.tenki.controller.location.dto.LocationDto;
import ru.yakaska.tenki.service.CurrentUserService;
import ru.yakaska.tenki.service.LocationService;

import java.util.List;

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
