package org.portfolio.springsecurityjwt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.portfolio.springsecurityjwt.security.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        try {
            System.out.println("trigged");
            return authenticationService.authenticate(authentication);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error executing authentication!", e);
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
            throw new RuntimeException("Error executing authentication with cookie response!", e);
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
            throw new RuntimeException("Error executing authentication with header response!", e);
        }
    }
    @GetMapping("/dontHaveAccess")
    public ResponseEntity<?> dontHaveAccess(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","You do not have access to the desired endpoint. Please access the '/authenticate' endpoint to obtain a token."));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> customLogout() {
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().body(Map.of("message", "Logout successful"));
    }
}