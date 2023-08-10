package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yakaska.tenki.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


}
