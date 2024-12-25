package six.sportswears.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/message") // Kênh nhận tin nhắn từ client
    @SendTo("/topic/messages") // Kênh gửi tin nhắn tới client
    public String processMessage(String message) {
        return "Server received: " + message;
    }
}
