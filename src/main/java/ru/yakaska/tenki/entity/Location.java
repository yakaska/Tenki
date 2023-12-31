package ru.yakaska.tenki.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    private String country;

    private String state;

    private Double latitude;

    private Double longitude;

}
