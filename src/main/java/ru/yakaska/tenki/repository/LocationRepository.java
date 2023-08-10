package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import ru.yakaska.tenki.entity.*;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
