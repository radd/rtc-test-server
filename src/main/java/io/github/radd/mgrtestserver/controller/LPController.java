package io.github.radd.mgrtestserver.controller;

import com.google.common.collect.Lists;
import io.github.radd.mgrtestserver.util.LongPollingClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/lp")
public class LPController {

    private Map<DeferredResult, LongPollingClient> clients = new ConcurrentHashMap<DeferredResult, LongPollingClient>();
    private List<String> messages = new ArrayList<>();

    @PostMapping("/send")
    public String sendMsg(@RequestBody String payload) {

        synchronized (messages){
            messages.add(payload);
        }

        sendToAll();

        return "ok";
    }

    // send messages to all connected client
    private void sendToAll() {
        clients.entrySet().forEach(entry -> {
            if(!entry.getValue().getDeferredResult().isSetOrExpired()) {
                List<String> msgList = prepareMessage(entry.getValue().getNextID());
                entry.getValue().getDeferredResult().setResult(msgList);
            }
            clients.remove(entry.getKey());
        });
    }

   private  List<String> prepareMessage(Integer nextMsg) {
       synchronized (messages){
           List<String> temp = Lists.newArrayList(messages.subList(nextMsg - 1, messages.size()));
           return temp;
       }
   }

    @GetMapping("/get")
    public DeferredResult<List<String>> getMsgs(@RequestParam("next") Integer nextMsg) {

        final DeferredResult<List<String>> deferredResult = new DeferredResult<>();

        List<String> msgs = prepareMessage(nextMsg);

        if(msgs.size() > 0) {
            deferredResult.setResult(msgs);
        }
        else {
            clients.put(deferredResult, new LongPollingClient(nextMsg, deferredResult));

            deferredResult.onCompletion(new Runnable() {
                public void run() {
                    clients.remove(deferredResult);
                }
            });
        }

        return deferredResult;
    }

    @GetMapping("/reset")
    public void remove() {
        System.out.println("========================R E S E T===============================");
        messages.clear();
        clients.clear();
    }

}
