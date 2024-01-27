package org.portfolio.springsecurityjwt.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.portfolio.springsecurityjwt.model.enums.Role;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Setter
    @Column(unique = true, nullable = false)
    private String username;
    @Setter
    @Column(unique = true,nullable = false)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Getter
    private Role role;

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
