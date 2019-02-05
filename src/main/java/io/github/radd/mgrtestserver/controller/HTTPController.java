package io.github.radd.mgrtestserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/http")
public class HTTPController {

    @PostMapping("/send")
    public String sendMsg(@RequestBody String payload) {
        //System.out.println(payload);
        return payload; // return received message
    }

}
