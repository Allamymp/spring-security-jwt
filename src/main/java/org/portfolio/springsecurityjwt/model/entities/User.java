package org.portfolio.springsecurityjwt.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "USERS", uniqueConstraints =@UniqueConstraint(columnNames = "userName"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Setter
    private String username;
    @Setter
    private String email;
    @Setter
    private String password;

}
