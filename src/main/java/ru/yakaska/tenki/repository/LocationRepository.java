package ru.yakaska.tenki.repository;

import org.springframework.data.jpa.repository.*;
import ru.yakaska.tenki.entity.*;

import java.util.*;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
