package ru.yakaska.tenki.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yakaska.tenki.controller.auth.AuthController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    @Test
    void AuthController_Smoke() {
        Assertions.assertThat(authController).isNotNull();
    }

    @Test
    void AuthController_RegisterOk() throws Exception {
        String loginRequestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/controller/login.json")));

        System.out.println(loginRequestBody);

        MvcResult mvcResult = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestBody)
                ).andExpect(status().isCreated())
                .andReturn();
        String loginResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(loginResponseBody).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    void AuthController_RegisterAlreadyRegistered() throws Exception {
        String loginRequestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/controller/login.json")));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestBody)
                ).andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBody)
        ).andExpect(status().isConflict());
    }

    @Test
    void AuthController_LoginOk() throws Exception {
        String loginRequestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/controller/login.json")));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestBody)
                ).andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestBody)
                ).andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void AuthController_LoginWrongPassword() throws Exception {
        String loginRequestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/controller/login.json")));

        mockMvc.perform(post("/auth/register")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(loginRequestBody)
                ).andExpect(status().isCreated())
               .andReturn();

        loginRequestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/controller/wrong_login.json")));

        mockMvc.perform(post("/auth/login")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(loginRequestBody)
                ).andExpect(status().isUnauthorized());
    }


}
