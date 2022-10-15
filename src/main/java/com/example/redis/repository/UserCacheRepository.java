package com.example.redis.repository;

import com.example.redis.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;

    public void setUser(User user) {
        String key = getKey(user.getId());
        log.info("the user {} {}", key, user.getName());
        userRedisTemplate.opsForValue().set(key, user);
    }

    public Optional<User> getUser(String userId) {
        String key = getKey(userId);
        User user = userRedisTemplate.opsForValue().get(key);
        log.info("get data from redis {} : {}", key, user);
        return Optional.of(user);
    }


    private String getKey(String userId) {
        return "USER:" + userId;
    }

}
