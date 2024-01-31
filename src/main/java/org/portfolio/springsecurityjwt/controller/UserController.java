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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService service;
    private final PasswordEncoder encoder;

    public UserController(UserService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping("create")
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
            throw new RuntimeException("Error creating user!");
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable @Validated String username) {
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

    @PostMapping("{username}-cookie")
    public ResponseEntity<?> getUserByUsernameCookie(@PathVariable @Validated String username, HttpServletResponse response) {
        try {
            User user = service.getUserByUsername(username);
            Cookie cookie = new Cookie("user",
                    user.getId() + "-"
                            + user.getUsername() + "-"
                            + user.getEmail() + "-"
                            + user.getPassword() + "-"
                            + user.getRole()
            );
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error finding user by username: " + username);
        }
    }

    @PostMapping("{username}-header")
    public ResponseEntity<?> getUserByUsernameHeader(@PathVariable @Validated String username) {
        User user = service.getUserByUsername(username);
        try {
            UserResponseRecord userRecord = new UserResponseRecord(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().getText());
            HttpHeaders headers = new HttpHeaders();
            headers.add("user-info", userRecord.toString());
            return ResponseEntity.ok().headers(headers).build();
        } catch (UserNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Error finding user by username: " + username);
        }
    }
}
