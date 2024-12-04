package com.example.demo.websocket;


import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.websocket.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

public class CustomWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisService redisService;
    
    // 채팅방별 연결 관리
    private final Map<String, List<WebSocketSession>> chatRooms = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("채팅연결");
        String roomId = getRoomId(session);
        ObjectMapper objectMapper = new ObjectMapper();
        //redisTemplate.delete("chat:" + roomId); 
        //redisTemplate.delete("chat:" + roomId);
        // 채팅방 세션 저장
        chatRooms.computeIfAbsent(roomId, k -> new ArrayList<>()).add(session);
        System.out.println("세션==="+session.getAttributes().get("user"));
        //System.out.println("레디스==="+(String) redisTemplate.opsForValue().get(session.getAttributes().get("user"))); 
        // 레디스 키값 체크 후 사용하자
          
        
        // Redis에서 메시지 로드
        List<String> messages = redisTemplate.opsForList().range("chat:" + roomId, 0, -1);

        // 메시지를 JSON 객체로 변환
        List<Map<String, Object>> parsedMessages = new ArrayList<>();
        System.out.println("messages========="+messages);
        for (String message : messages) {
            Map<String, Object> parsedMessage = objectMapper.readValue(message, Map.class);
            parsedMessages.add(parsedMessage);
        }

        // JSON 배열로 직렬화
        String messageListJson = objectMapper.writeValueAsString(parsedMessages);
        System.out.println("메시지(JSON 배열): " + messageListJson);

        // 클라이언트로 JSON 배열 전송
        session.sendMessage(new TextMessage(messageListJson));
    }




    
    //이건 사용자가 채팅 보낼때마다 레디스 저장...
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        String payload = message.getPayload();
        String userInfo = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String messageListJson = null;
        // 메시지 Redis에 저장
        Map<String, Object> parsedPayload = objectMapper.readValue(payload, Map.class);
        String userUuid =(String)session.getAttributes().get("user");
        if(userUuid!=null && redisService.getTokenKey(userUuid)) {
        	 userInfo = redisTemplate.opsForValue().get(session.getAttributes().get("user"));
        	 Map<String, Object> parsedMessage = objectMapper.readValue(userInfo, Map.class);
        	 
        	parsedPayload.put("userId",parsedMessage.get("userId"));
        	parsedPayload.put("username",parsedMessage.get("username"));
        	messageListJson = objectMapper.writeValueAsString(parsedPayload);
        }else {
        	objectMapper.writeValueAsString(parsedPayload);  	
        }
      
        //System.out.println("레디스==="+);
        
                  
        
        redisTemplate.opsForList().rightPush("chat:" + roomId,messageListJson.toString());
        
        
        // 같은 채팅방 사용자들에게 브로드캐스트
        for (WebSocketSession s : chatRooms.get(roomId)) {
        	
            if (s.isOpen()) {
            	System.out.println("사용자 보낸값 리턴값 =="+payload);
            	s.sendMessage(new TextMessage(payload));
               // s.sendMessage(new TextMessage(messageListJson));
            }
        }
        
        
    }

    //이건 소켓 닫힐때....
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = getRoomId(session);
        chatRooms.get(roomId).remove(session);
    }

    private String getRoomId(WebSocketSession session) {
        // 요청 파라미터로 방 ID 가져오기
        String query = session.getUri().getQuery();
        return query.split("=")[1]; // 예: ?roomId=123
    }
}


