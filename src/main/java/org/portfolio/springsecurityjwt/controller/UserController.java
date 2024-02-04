package org.portfolio.springsecurityjwt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.portfolio.springsecurityjwt.exception.UserNotFoundException;
import org.portfolio.springsecurityjwt.model.entities.User;
import org.portfolio.springsecurityjwt.model.enums.Role;
import org.portfolio.springsecurityjwt.model.record.UserCreateRecord;
import org.portfolio.springsecurityjwt.model.record.UserResponseRecord;
import org.portfolio.springsecurityjwt.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService service;
    private final PasswordEncoder encoder;

    public UserController(UserService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Validated UserCreateRecord data) {
        try {
            service.create(
                    new User(
                            data.username(),
                            data.email(),
                            encoder.encode(data.password()),
                            Role.fromText(data.role())
                    )
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating user!", e);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            User user = service.getUserByUsername(username);
            return ResponseEntity.ok().body(
                    new UserResponseRecord(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getRole().getText()
                    )
            );
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by username: " + username, e);
        }
    }

    @PostMapping("/find-cookie")
    public ResponseEntity<?> getUserByUsernameCookie(@RequestParam String username, HttpServletResponse response) {
        try {
            User user = service.getUserByUsername(username);

            String cookieValue = String.format(
                    "%s-%s-%s-%s-%s",
                    user.getId()
                    , user.getUsername()
                    , user.getEmail()
                    , user.getPassword()
                    , user.getRole());

            Cookie cookie = new Cookie("user", cookieValue);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok().build();

        } catch (UserNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error finding user by username: " + username, e);
        }
    }

    @PostMapping("/find-header")
    public ResponseEntity<?> getUserByUsernameHeader(@RequestParam String username) {

        try {
            User user = service.getUserByUsername(username);
            UserResponseRecord userRecord = new UserResponseRecord(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().getText());

            HttpHeaders headers = new HttpHeaders();
            headers.add("user-info", userRecord.toString());

            return ResponseEntity.ok().headers(headers).build();

        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by username: " + username, e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseRecord>> getAll() {
        try {
            List<UserResponseRecord> list = service.getAll().stream()
                    .map(user -> new UserResponseRecord(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getRole().getText()
                    ))
                    .collect(Collectors.toList());

            return list.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok().body(list);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error finding all users: " + e.getMessage());
        }
    }
}
