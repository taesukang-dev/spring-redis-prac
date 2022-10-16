package com.example.redis.controller;

import com.example.redis.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;


    // websocket으로 들어오는 메세지 발행 처리
    // Client에서 /pub/chat/message 로 발행 요청 하면
    // 여기에서 처리하고
    // 메세지가 발행되면 /sub/chat/room/{roomId} 로 메세지 send
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
