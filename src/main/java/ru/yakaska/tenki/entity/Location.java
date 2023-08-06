package ru.yakaska.tenki.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(unique = true)
    private String name;

    private Double latitude;

    private Double longitude;

    @ManyToMany(mappedBy = "locations")
    private Set<User> users;

}
