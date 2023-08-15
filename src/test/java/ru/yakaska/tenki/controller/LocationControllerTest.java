package ru.yakaska.tenki.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yakaska.tenki.controller.location.LocationController;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationController locationController;

    @Test
    void LocationController_Smoke() {
        Assertions.assertThat(locationController).isNotNull();
    }


    @Test
    void LocationController_AddLocation_ReturnsLocationDto() {

    }

    @Test
    void LocationController_GetAllLocations_ReturnsListLocationDto() {

    }

    @Test
    void LocationController_GetById_ReturnsLocationDto() {

    }

    @Test
    void LocationController_DeleteById_ReturnsNoContent() {

    }


}
