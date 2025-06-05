package com.back2basics.user.port.out;

public interface PasswordResetTokenPort {

    String createToken(String username);

    String getUsernameByToken(String token);

    void deleteToken(String token);
}

