package ru.yakaska.tenki.service;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.yakaska.tenki.api.OpenWeatherApi;
import ru.yakaska.tenki.controller.location.dto.LocationDto;
import ru.yakaska.tenki.entity.Location;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.repository.UserRepository;
import ru.yakaska.tenki.service.dto.weather.WeatherResponse;
import ru.yakaska.tenki.service.impl.LocationServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.mockito.BDDMockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource("classpath:application-test.yml")
@SpringBootTest
class LocationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OpenWeatherApi openWeatherApi;

    @InjectMocks
    private LocationServiceImpl locationService;


    private User user;

    private WeatherResponse weatherResponse;

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
                .id(5544234L)
                .country("Russia")
                .state("Moscow")
                .name("Moscow")
                .latitude(12.12)
                .longitude(12.123)
                .build();
        user.getLocations().add(location);

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/weather.json");
        weatherResponse = objectMapper.readValue(file, WeatherResponse.class);

    }

    @Test
    void LocationService_GetAllLocations_ReturnsListLocationDto() {
        when(openWeatherApi.fetchWeather(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(weatherResponse);

        List<LocationDto> locationDtos = locationService.getAllLocations(user);

        Assertions.assertThat(locationDtos)
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(locationDtos.size()).isEqualTo(1);
        Assertions.assertThat(locationDtos.get(0).getId()).isEqualTo(location.getId());

    }


}
