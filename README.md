# Real-time communication test server

This server application provides testing environment for:
- HTTP Long Polling
- Server-Sent Events
- WebSocket
- WebSocket + STOMP

See [Client appliaction](https://github.com/radd/rtc-test-client)

## Run

Use the Maven wrapper **mvnw.cmd** to make jar file.

```bash
mvnw package
```

Next run jar file in **target** folder

```bash
java -jar mgr-test-server-0.0.1-SNAPSHOT.jar
```
