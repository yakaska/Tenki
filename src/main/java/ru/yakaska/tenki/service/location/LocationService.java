package ru.yakaska.tenki.service.location;

import ru.yakaska.tenki.dto.*;

import java.math.*;
import java.util.*;

public interface LocationService {

    LocationDto search(String locationName);

    List<LocationDto> findAll();

    LocationDto add(String locationName);

    LocationDto delete(String locationName);

}
