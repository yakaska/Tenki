package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakaska.tenki.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {


}
