package org.portfolio.springsecurityjwt.model.record;

public record UserCreateRecord(String username, String password, String email, String role) {
}
