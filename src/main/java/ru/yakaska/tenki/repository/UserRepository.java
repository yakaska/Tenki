package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakaska.tenki.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);




}
