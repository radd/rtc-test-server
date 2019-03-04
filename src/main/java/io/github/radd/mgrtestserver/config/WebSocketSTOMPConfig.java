package io.github.radd.mgrtestserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketSTOMPConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic/");
        config.setApplicationDestinationPrefixes("/ws"); // for send messages e.g. wsClient.send('/ws/'@MessageMapping',...
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/send").setAllowedOrigins("*"); // for connect to ws server e.g. Stomp.overWS('ws://localhost:8080/send');
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(Integer.MAX_VALUE);
        registration.setSendBufferSizeLimit(Integer.MAX_VALUE);
        registration.setSendTimeLimit(20000);
    }



}
