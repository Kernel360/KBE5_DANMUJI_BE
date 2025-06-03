package com.back2basics.util;

import static com.back2basics.security.code.RedisErrorCode.REDIS_CONNECTION_ERROR;

import com.back2basics.security.exception.CustomRedisConnectionException;
import io.lettuce.core.RedisConnectionException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, Object val, Long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, val, time, timeUnit);
        } catch (RedisConnectionFailureException | RedisConnectionException e) {
            throw new CustomRedisConnectionException(REDIS_CONNECTION_ERROR);
        } catch (Exception e) {
            log.error("Failed to save key {} to Redis", key, e);
        }
    }

    public void setDataExpire(String key, String value, long durationMillis) {
        redisTemplate.opsForValue().set(key, value, durationMillis, TimeUnit.MILLISECONDS);
    }

    public String getData(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public String buildRefreshTokenKey(String username) {
        return "RT:" + username;
    }

    public String buildBlacklistTokenKey(String accessToken) {
        return "BL:" + accessToken;
    }

}
