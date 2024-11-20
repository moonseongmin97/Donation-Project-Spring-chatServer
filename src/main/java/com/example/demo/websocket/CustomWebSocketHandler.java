package com.example.demo.websocket;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {


	/**
     * 웹소켓 연결 성공시
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        try  {
        	    System.out.println("WebSocket connection established"+session);
        	    session.sendMessage(new TextMessage("Hello, Client!"));
        	
        } catch (Exception e) {
        	e.getStackTrace();
        }
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 받은 메시지
        String clientMessage = message.getPayload();
        System.out.println("Received from client: " + clientMessage);
        // 클라이언트에게 응답 메시지 보내기
        String serverMessage = "서버에서 받은 메시지: " + clientMessage;
        session.sendMessage(new TextMessage(serverMessage));
    }
    
}

