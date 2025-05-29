package com.back2basics.user.model;

import java.time.LocalDateTime;
import lombok.Builder;

public class PasswordResetToken {

    Long id;
    String token;
    String email;
    LocalDateTime expiredAt;
    boolean used;

    @Builder
    public PasswordResetToken(String token, String email, LocalDateTime expiredAt, boolean used) {
        this.token = token;
        this.email = email;
        this.expiredAt = expiredAt;
        this.used = used;
    }

    public boolean isValid() {
        return !used && expiredAt.isAfter(LocalDateTime.now());
    }

    public PasswordResetToken markUsed() {
        return new PasswordResetToken(token, email, expiredAt, true);
    }
}
