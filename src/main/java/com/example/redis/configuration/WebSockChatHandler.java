package com.example.redis.configuration;

import com.example.redis.domain.ChatMessage;
import com.example.redis.dto.ChatRoom;
import com.example.redis.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    // client가 발송한 메세지 받아서 처리함

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class); // payload 받아서
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId()); // chatroom 가져오고
        room.handleActions(session, chatMessage, chatService);
        // room에 저장돼 있는 모든 session에 메세지 전송
        // 실제 전송은 chatroom에 map으로 저장 돼 있는 session 객체가
    }
}
