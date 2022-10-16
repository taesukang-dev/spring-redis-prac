package com.example.redis.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom implements Serializable {
    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

}
