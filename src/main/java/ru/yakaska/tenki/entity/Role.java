package ru.yakaska.tenki.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
