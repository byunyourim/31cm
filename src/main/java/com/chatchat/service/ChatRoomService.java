package com.chatchat.service;

import com.chatchat.domain.ChatRoom;
import com.chatchat.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ChatRoomService {

    public ChatRoom createNewRoom(User creator, List<User> participants, LocalDateTime createDate) {

        String roomId = UUID.randomUUID().toString();
        String roomName = participants.stream()
                .map(s -> s.getName()).toString();

        ChatRoom chatRoom = ChatRoom.builder()
                .id(roomId)
                .name(roomName)
                .createdAt(createDate)
                .creator(creator)
                .participants(participants)
                .build();

        return chatRoom;
    }

    public List<ChatRoom> list(Long loginId) {
        List<String> participants = null;
        //List<ChatRoom> rooms = List.of(new ChatRoom(), new ChatRoom());

        return null;
    }
}
