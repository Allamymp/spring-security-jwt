package org.portfolio.springsecurityjwt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.portfolio.springsecurityjwt.model.entities.User;
import org.portfolio.springsecurityjwt.model.enums.Role;
import org.portfolio.springsecurityjwt.model.record.UserCreateRecord;
import org.portfolio.springsecurityjwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClientController {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    public ClientController(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Validated UserCreateRecord data) {
        repository.save(new User(
                data.username(),
                data.email(),
                encoder.encode(data.password()),
                Role.fromText(data.role())
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable @Validated String username) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("{username}-cookie")
    public ResponseEntity<?> getUserByUsernameCookie(@PathVariable @Validated String username, HttpServletResponse response) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            Cookie cookie = new Cookie("user",
                    user.get().getId() + "-"
                            + user.get().getUsername() + "-"
                            + user.get().getEmail() + "-"
                            + user.get().getPassword() + "-"
                            + user.get().getRole()
            );
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
