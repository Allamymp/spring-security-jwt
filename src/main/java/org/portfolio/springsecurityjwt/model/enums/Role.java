package org.portfolio.springsecurityjwt.model.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("admin"),
    USER("user"),
    DEVELOPER("developer");

    private final String text;

    Role(String text) {
        this.text = text;
    }

    public static Role fromText(String text) {
        for (Role role : Role.values()) {
            if (role.getText().equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant for text: " + text);
    }
}
