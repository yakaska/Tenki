package ru.yakaska.tenki.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.jdbc.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import ru.yakaska.tenki.entity.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository_Save_ReturnsUser() {

        User user = User.builder()
                .username("Yakaska")
                .password("password")
                .build();

        User savedUser = userRepository.save(user);


        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isPositive();
        Assertions.assertThat(savedUser.getUsername()).isNotEmpty();
        Assertions.assertThat(savedUser.getLocations()).isNull();
    }

    @Test
    void UserRepository_FindByUsername_ReturnsUser() {

        User user = User.builder()
                .username("Yakaska")
                .password("password")
                .build();

        userRepository.save(user);

        User foundUser = userRepository.findByUsername("Yakaska").get();

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("Yakaska");

    }


}
