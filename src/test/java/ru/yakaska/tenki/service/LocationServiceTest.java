package ru.yakaska.tenki.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.TestPropertySource;
import ru.yakaska.tenki.api.OpenWeatherApi;
import ru.yakaska.tenki.controller.location.dto.LocationDto;
import ru.yakaska.tenki.entity.Location;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.repository.UserRepository;
import ru.yakaska.tenki.service.dto.search.SearchItem;
import ru.yakaska.tenki.service.dto.weather.WeatherResponse;
import ru.yakaska.tenki.service.impl.LocationServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource("classpath:application-test.yml")
class LocationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OpenWeatherApi openWeatherApi;

    @InjectMocks
    private LocationServiceImpl locationService;


    private User user;

    private WeatherResponse weatherResponse;

    private List<SearchItem> searchResponse;

    private Location location;

    @BeforeEach
    public void setup() throws IOException {
        user = User.builder()
                .id(1L)
                .username("Yakaska")
                .password("Password")
                .locations(new HashSet<>())
                .build();
        location = Location.builder()
                .id(54746L)
                .country("Russia")
                .state("Moscow")
                .name("Moscow")
                .latitude(12.12)
                .longitude(12.123)
                .build();
        user.getLocations().add(location);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File weatherFile = new File("src/test/resources/weather.json");
        weatherResponse = objectMapper.readValue(weatherFile, WeatherResponse.class);
        File searchFile = new File("src/test/resources/search.json");
        searchResponse = objectMapper.readValue(searchFile, new TypeReference<List<SearchItem>>() {
        });
    }

    @Test
    void LocationService_GetAllLocations_ReturnsListLocationDto() {
        Mockito.when(openWeatherApi.fetchWeather(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(weatherResponse);
        List<LocationDto> locationDtos = locationService.getAllLocations(user);
        Assertions.assertThat(locationDtos)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(locationDtos.size()).isEqualTo(1);
        Assertions.assertThat(locationDtos.get(0).getId()).isEqualTo(location.getId());
    }


    @Test
    void LocationService_GetAllLocations_WithEmptyLocations_ReturnsEmptyList() {
        user.getLocations().clear();
        List<LocationDto> locationDtos = locationService.getAllLocations(user);
        Assertions.assertThat(locationDtos)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void LocationService_GetLocationById_ReturnsLocationDto() {
        Mockito.when(openWeatherApi.fetchWeather(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(weatherResponse);
        LocationDto locationDto = locationService.getLocationById(location.getId(), user);
        Assertions.assertThat(locationDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", location.getId());
    }

    @Test
    void LocationService_GetLocationById_WithEmptyLocations_ThrowsResourceNotFoundException() {
        user.getLocations().clear();
        Assertions.assertThatThrownBy(
                () -> locationService.getLocationById(location.getId(), user)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void LocationService_GetLocationById_WithWrongId_ThrowsResourceNotFoundException() {
        user.getLocations().clear();
        Assertions.assertThatThrownBy(
                () -> locationService.getLocationById(123L, user)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void LocationService_SearchLocation_ReturnsListLocationDto() {
        Mockito.when(openWeatherApi.fetchWeather(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(weatherResponse);
        Mockito.when(openWeatherApi.search("Lughaye")).thenReturn(searchResponse);

        List<LocationDto> locationDtos = locationService.searchLocation("Lughaye");

        Assertions.assertThat(locationDtos.size()).isEqualTo(1);
        Assertions.assertThat(locationDtos.get(0).getId()).isEqualTo(54746);
    }

    @Test
    void LocationService_SearchLocation_WithMalformedName_ThrowsResourceNotFoundException() {
        Assertions.assertThatThrownBy(
                () -> locationService.searchLocation("sldkfjskdohsdfkljewh")
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void LocationService_SearchLocation_WithEmptyName_ThrowsResourceNotFoundException() {
        Assertions.assertThatThrownBy(
                () -> locationService.searchLocation("")
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void LocationService_AddLocation_ReturnsLocationDto() {
        user.getLocations().clear();

        LocationDto locationDto = LocationDto.builder()
                .id(location.getId())
                .country(location.getCountry())
                .state(location.getState())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .description(weatherResponse.getWeather().get(0).getDescription())
                .temperature(weatherResponse.getMain().getTemp())
                .build();
        LocationDto addResultDto = locationService.addLocation(locationDto, user);

        Assertions.assertThat(addResultDto).isNotNull();
        Assertions.assertThat(addResultDto.getId()).isEqualTo(location.getId());
        Assertions.assertThat(user.getLocations().size()).isEqualTo(1);
    }

    @Test
    void LocationService_DeleteLocationById_DeletesLocation() {
        locationService.deleteLocationById(location.getId(), user);
        Assertions.assertThat(user.getLocations().size()).isEqualTo(0);
    }

}
