//package dev.project1backendspring.Config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@CrossOrigin(origins = "http://localhost:3000")
//public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/socket/shows")
//                .setAllowedOrigins("http://localhost:3000")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableSimpleBroker("/topic");
//    }
//}