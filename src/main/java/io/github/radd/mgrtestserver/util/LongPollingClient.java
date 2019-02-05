package io.github.radd.mgrtestserver.util;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public class LongPollingClient {

    private final int nextID; // next id of message to send
    private final DeferredResult<List<String>> deferredResult;

    public LongPollingClient(final int nextID, final DeferredResult<List<String>> deferredResult) {
        this.nextID = nextID;
        this.deferredResult = deferredResult;
    }

    public int getNextID() {
        return nextID;
    }

    public DeferredResult<List<String>> getDeferredResult() {
        return deferredResult;
    }
}
