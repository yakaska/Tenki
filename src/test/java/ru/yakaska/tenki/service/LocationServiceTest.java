package ru.yakaska.tenki.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.*;
import ru.yakaska.tenki.api.OpenWeatherApi;
import ru.yakaska.tenki.controller.location.dto.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.service.dto.search.SearchItem;
import ru.yakaska.tenki.service.impl.*;

import java.util.*;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
class LocationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private User user;

    private Location location;

    @BeforeEach
    public void setup() {
        location = Location.builder()
                .country("Russia")
                .state("Moscow")
                .name("Moscow")
                .longitude(51.41)
                .latitude(35.12)
                .build();
        Set<Location> locations = new HashSet<>();
        locations.add(location);
        user = User.builder()
                .username("Yakaska")
                .password("password")
                .locations(locations)
                .build();
    }

    @Test
    @WithMockUser
    void LocationService_AddLocation_ReturnsLocationDto() {
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
        LocationDto savedLocation = locationService.addLocation(locationDto, user);
        Assertions.assertThat(savedLocation)
                .isNotNull()
                .isEqualTo(locationDto);
    }

    @Test
    void LocationService_DeleteById_DeletesOk() {
        locationService.deleteLocationById(location.getId(), user);
        Assertions.assertThat(user.getLocations()).isEmpty();
    }

    @Test
    void LocationService_Search_Returns_LocationsDto() {
        List<LocationDto> locationDtos = locationService.searchLocation("Ivanovo");

        Assertions.assertThat(locationDtos).isNotEmpty();
    }


}
