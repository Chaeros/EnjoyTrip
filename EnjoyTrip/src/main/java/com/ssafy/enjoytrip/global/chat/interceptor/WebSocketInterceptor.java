package com.ssafy.enjoytrip.global.chat.interceptor;

import com.ssafy.enjoytrip.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class WebSocketInterceptor implements HandshakeInterceptor  {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("웹소켓 검증시작");
        URI uri = request.getURI();
        String query = uri.getQuery();
        String token = extractTokenFromQuery(query);
        System.out.println("temp token="+token);
        HttpHeaders headers = request.getHeaders();
        String authorizationHeader = headers.getFirst(jwtService.getAccessHeader());
        System.out.println("웹소켓 진입 Authorization = "+authorizationHeader);
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return false;
//        }
//        String token = authorizationHeader.replace("Bearer ", "");

        if (token == null || !jwtService.isTokenValid(token)) {
            return false;
        }
        System.out.println("웹소켓 JWT 검증 통과");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private String extractTokenFromQuery(String query) {
        for (String param : query.split("&")) {
            if (param.startsWith("token=")) {
                return param.substring("token=".length());
            }
        }
        return null;
    }

//    @SneakyThrows
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//        if (accessor.getCommand() == StompCommand.CONNECT) {
//            String authToken = accessor.getFirstNativeHeader("Authorization");
//
//            if (authToken == null || !jwtService.isTokenValid(authToken)) {
//                throw new AuthException("Authentication failed!!");
//            }
//        }
//        return message;
//    }

}