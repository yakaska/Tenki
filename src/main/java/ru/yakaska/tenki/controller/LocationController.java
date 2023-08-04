package ru.yakaska.tenki.controller;

import lombok.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.service.location.*;

import java.util.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/")
    public List<LocationDto> getAll() {
        return locationService.findAll();
    }

}
