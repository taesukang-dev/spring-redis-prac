package com.example.redis.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@EnableWebSocket
@Configuration
public class WebSockConfig implements WebSocketConfigurer {
    // handler 등록

    private final WebSockChatHandler webSockChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSockChatHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
