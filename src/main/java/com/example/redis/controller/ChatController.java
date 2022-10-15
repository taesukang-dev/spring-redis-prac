package com.example.redis.controller;

import com.example.redis.dto.ChatRoom;
import com.example.redis.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom creatRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }


}
