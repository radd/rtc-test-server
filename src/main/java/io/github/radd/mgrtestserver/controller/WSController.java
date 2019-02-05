package io.github.radd.mgrtestserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {

    @MessageMapping("/send") // client send message to ws(endpoint)/send
    @SendTo("/topic/receive") // subscribe
    public String processMsg(@Payload String payload) {
        //System.out.println(payload);
        return payload; // return received message
    }

}
