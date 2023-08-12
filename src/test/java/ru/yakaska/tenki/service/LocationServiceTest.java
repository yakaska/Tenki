package ru.yakaska.tenki.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.jdbc.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.context.*;
import org.springframework.security.test.context.support.*;
import ru.yakaska.tenki.controller.location.dto.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.impl.*;

import java.util.*;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @InjectMocks
    private CurrentUserService currentUserService;

    @Test
    @WithMockUser
    void LocationService_AddLocation_ReturnsLocationDto() {
        Location location = Location.builder()
                .country("Russia")
                .state("Moscow")
                .name("Moscow")
                .longitude(51.41)
                .latitude(35.12)
                .build();
        Set<Location> locations = new HashSet<>();
        locations.add(location);
        User user = User.builder()
                .username("Yakaska")
                .password("password")
                .locations(locations)
                .build();

        when(currentUserService.getCurrentUser()).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        LocationDto locationDto = LocationDto.builder()
                .country("Russia")
                .state("Moscow")
                .name("Moscow")
                .longitude(51.41)
                .latitude(35.12)
                .temperature(12.4)
                .description("Rain")
                .build();


        LocationDto savedLocation = locationService.addLocation(locationDto);

        Assertions.assertThat(savedLocation)
                .isNotNull()
                .isEqualTo(locationDto);

    }


}
