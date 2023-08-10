package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import ru.yakaska.tenki.entity.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
}
