package org.portfolio.springsecurityjwt.model.enums;


import lombok.Getter;

@Getter
public enum Role {

    ADMIN("Admin"),
    USER("User"),
    DEVELOPER("Developer");

    private final String text;

    Role(String text) {
        this.text = text;
    }
}
