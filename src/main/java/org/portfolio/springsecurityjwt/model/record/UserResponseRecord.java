package org.portfolio.springsecurityjwt.model.record;

import java.util.UUID;

public record UserResponseRecord(UUID id, String userName, String email, String password, String role) {
}
