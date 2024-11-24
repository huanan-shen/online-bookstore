package org.anthony.bs.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private static final Logger logger = Logger.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean tryLock(String key) {
        return tryLock(key, 5 * 60);
    }

    public boolean tryLock(String key, long expireTime) {
        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, 1, expireTime, TimeUnit.SECONDS);
            if (result == null) {
                return false;
            }
            return result;
        } catch (Exception e) {
            logger.error(String.format("redis err :%s", e.getMessage()), e);
            return false;
        }
    }

    public void releaseLock(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error(String.format("redis err :%s", e.getMessage()), e);
        }

    }
}
