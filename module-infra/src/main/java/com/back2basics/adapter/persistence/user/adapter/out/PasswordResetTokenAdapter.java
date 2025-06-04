package com.back2basics.adapter.persistence.user.adapter.out;

import com.back2basics.user.port.out.PasswordResetTokenPort;
import com.back2basics.util.RedisUtil;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenAdapter implements PasswordResetTokenPort {

    private final RedisUtil redisUtil;
    private static final long TTL = 30L;

    @Override
    public String createToken(String username) {
        String token = UUID.randomUUID().toString();
        redisUtil.save("pw-reset:" + token, username, TTL, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public String getUsernameByToken(String token) {
        return redisUtil.getData("pw-reset:" + token);
    }

    @Override
    public void deleteToken(String token) {
        redisUtil.delete("pw-reset:" + token);
    }
}

