package com.example.server.controller;

import com.example.server.pojo.Admin;
import com.example.server.pojo.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * @Description webSocket接口
 */
@Controller
public class WsController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 由于是websocket，所以是@MessageMapping，而不是@GetMapping、@PostMapping等
     * websocket协议使得服务器可以主动向客户端推送消息，注意，这里的的聊天实现中，不是“客户端1-客户端2”这样直接通信，
     * 而是"客户端1-服务器-客户端2"而这样通过服务器来中转进行消息通信，服务器发送消息给客户端2就是主动推送消息
     * @param authentication
     * @param chatMsg
     */
    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg){
        Admin admin = (Admin) authentication.getPrincipal();
        chatMsg.setFrom(admin.getUsername());
        chatMsg.setFromNickName(admin.getName());
        chatMsg.setDate(LocalDateTime.now());

        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
    }
}
