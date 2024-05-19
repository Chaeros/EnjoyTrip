package com.ssafy.enjoytrip.global.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.global.chat.dto.ChatMessageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // chatRoomId: {session1, session2}
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new TreeMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        log.info("payload {}", payload);

        // 페이로드 -> chatMessageDto로 변환
        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
        log.info("session {}", chatMessageDto.toString());

        Long chatRoomId = chatMessageDto.getChatRoomId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        // 메시지 타입에 따라 처리
        if (chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
            chatRoomSession.add(session);
        }
        else if (chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.LEAVE)) {
            handleLeaveMessage(session);
        }
        if (chatRoomSession.size() >= 3) {
            removeClosedSession(chatRoomSession);
        }
        sendMessageToChatRoom(chatMessageDto, chatRoomSession);
    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        removeSessionFromAllRooms(session);
        removeRooms(session);
        sessions.remove(session);
    }

    // 모든 채팅방에서 해당 세션 제거
    private void handleLeaveMessage(WebSocketSession session) {
        removeSessionFromAllRooms(session);
        removeRooms(session);
        sessions.remove(session);
    }

    // 모든 채팅방에서 해당 세션 제거하는 메서드
    private void removeSessionFromAllRooms(WebSocketSession session) {
        for (Map.Entry<Long, Set<WebSocketSession>> entry : chatRoomSessionMap.entrySet()) {
            Set<WebSocketSession> chatRoomSessions = entry.getValue();
            if ( chatRoomSessions.contains(session) ){
                System.out.println(entry.getKey() + "방 퇴장");
            }
            chatRoomSessions.remove(session);
        }
    }

    // 접속한 인원이 없는 채팅방 세션에서 삭제
    private void removeRooms(WebSocketSession session) {
        ArrayList<Long> emptyRoom = new ArrayList<>();
        for (Map.Entry<Long, Set<WebSocketSession>> entry : chatRoomSessionMap.entrySet()) {
            Set<WebSocketSession> chatRoomSessions = entry.getValue();
            if (chatRoomSessions.isEmpty()) {
                emptyRoom.add(entry.getKey());
            }
        }
        for ( Long roomId : emptyRoom ){
            System.out.println(roomId + "방 제거");
            chatRoomSessionMap.remove(roomId);
        }
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            } else {
                log.warn("Attempted to send message to a closed session: {}", session.getId());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
