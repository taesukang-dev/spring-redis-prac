package com.example.redis.repository;

import com.example.redis.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserCacheRepositoryTest {

    @Autowired
    UserCacheRepository userCacheRepository;

    @Test
    void setting() throws Exception {
        // given
        User user = new User("test2", "test1", 20);
        userCacheRepository.setUser(user);
        // when
        // then
        Optional<User> found = userCacheRepository.getUser(user.getId());
        Assertions.assertThat(found.get().getId()).isEqualTo(user.getId());
    }
}