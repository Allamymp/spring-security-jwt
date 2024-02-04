package org.portfolio.springsecurityjwt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.portfolio.springsecurityjwt.security.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        try {
            return authenticationService.authenticate(authentication);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/authenticate-cookie")
    public ResponseEntity<?> authenticateCookie(Authentication authentication, HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("token", authenticationService.authenticate(authentication));
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/authenticate-header")
    public ResponseEntity<?> authenticateHeader(Authentication authentication) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer "
                    + authenticationService.authenticate(authentication));
            return ResponseEntity.ok().headers(headers).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}