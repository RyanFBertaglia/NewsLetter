package com.newsletter.controller;

import com.newsletter.service.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send-delayed-message")
    public ResponseEntity<String> sendDelayedMessage(
            @RequestParam String message,
            @RequestParam long delayMillis) {
        messageProducer.sendMessageToDelayQueue(message, delayMillis);
        return ResponseEntity.ok("Mensagem '" + message + "' enviada para a fila de atraso com " + delayMillis + " ms de atraso.");
    }
}
