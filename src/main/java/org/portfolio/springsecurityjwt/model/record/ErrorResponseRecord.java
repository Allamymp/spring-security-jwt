package org.portfolio.springsecurityjwt.model.record;

import java.time.LocalDateTime;

public record ErrorResponseRecord(String message, String details, LocalDateTime timeStamp) {
}
