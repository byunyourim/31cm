package com.chatchat.controller;

import com.chatchat.domain.ChatRoom;
import com.chatchat.domain.User;
import com.chatchat.service.ChatRoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class chatControllerTest {

    @Test
    @DisplayName("새로운 채팅 방 생성")
    public void testCreatNewRoom() {

        ChatRoomService chatRoomService = new ChatRoomService();

        Long loginId = 1L;

        User creator = new User(loginId);
        List<User> participants = List.of(new User(2L), new User(3L));
        LocalDateTime createdAt = LocalDateTime.now();

        ChatRoom chatRoom = chatRoomService.createNewRoom(creator, participants, createdAt);

        assertEquals(creator, chatRoom.getCreator());
        assertEquals(2, chatRoom.getParticipants().size());
        assertEquals(createdAt, chatRoom.getCreatedAt());

    }

    @Test
    @DisplayName("채팅 방 목록 조회")
    public void testGetRooms() {
        Long loginId = 1L;

        ChatRoomService chatRoomService = new ChatRoomService();

        List<ChatRoom> rooms = chatRoomService.list(loginId);

        assertEquals(2, rooms.size());
    }
}
