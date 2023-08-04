package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.*;
import ru.yakaska.tenki.entity.*;

import java.util.*;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);

}
