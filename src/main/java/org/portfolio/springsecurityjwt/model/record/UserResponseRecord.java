package org.portfolio.springsecurityjwt.model.record;

import org.portfolio.springsecurityjwt.model.enums.Role;

import java.util.UUID;

public record UserResponseRecord(UUID id, String userName, String email, String password, String role) {
}
