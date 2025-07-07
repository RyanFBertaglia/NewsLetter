package com.newsletter.controller;

import com.newsletter.model.DelayedMessageRequest;
import com.newsletter.service.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageProducer messageProducer;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/schedule-message")
    public ResponseEntity<String> scheduleMessage(@RequestBody DelayedMessageRequest request) {
        try {
            LocalDateTime targetDateTime = LocalDateTime.parse(request.targetDate(), DATE_FORMATTER);
            LocalDateTime currentDateTime = LocalDateTime.now();

            long delayMillis = targetDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() -
                    currentDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

            if (delayMillis < 0) {
                return ResponseEntity.badRequest().body("A data alvo deve ser no futuro.");
            }

            messageProducer.sendDelayedMessage(request, delayMillis);

            return ResponseEntity.ok("Mensagem agendada para " + request.targetDate() +
                    " para o destinatário " + request.sendTo());

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato de data inválido. Use 'yyyy-MM-dd HH:mm:ss'.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao agendar mensagem.");
        }
    }
}