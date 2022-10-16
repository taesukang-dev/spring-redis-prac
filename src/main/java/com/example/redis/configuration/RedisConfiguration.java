package com.example.redis.configuration;

import com.example.redis.domain.ChatMessage;
import com.example.redis.domain.User;
import com.example.redis.service.RedisSubService;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisURI redisURI = RedisURI.create(host, port);
        org.springframework.data.redis.connection.RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisURI);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() { // 정해진 채널로 들어온 메세지를 처리할 action 정의
        return new MessageListenerAdapter(new RedisSubService());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() { // JMS 메시징을 사용하는 컴포넌트, 비동기 메세지를 받는데 사용
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
//        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("chatroom");
    }

    @Bean
    public RedisTemplate<String, ChatMessage> chatRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ChatMessage> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<ChatMessage>(ChatMessage.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return redisTemplate;
    }
}
