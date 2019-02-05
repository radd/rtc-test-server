package io.github.radd.mgrtestserver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sse")
public class SSEController {

    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/reg")
    public SseEmitter register(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        int clientID = Integer.parseInt(request.getParameter("id"));

        SseEmitter emitter = new SseEmitter(360_000L); // set timeout to 5 minutes
        this.emitters.put(clientID, emitter);

        emitter.onCompletion(() -> {
            //System.out.println("client onCompletion");
            this.emitters.remove(clientID);
        });

        //System.out.println("req client id: " + clientID);
        return emitter;
    }


    @PostMapping("/send")
    public String sendMsg(@RequestBody String payload) {
        emitters.entrySet().forEach(entry -> {
            try {
                entry.getValue().send(payload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return payload;
    }

    @GetMapping("/reset")
    public void reset() {
        System.out.println("========================R E S E T===============================");
        emitters.entrySet().forEach(entry -> {
            entry.getValue().complete();
        });

        emitters.clear();
    }

}
