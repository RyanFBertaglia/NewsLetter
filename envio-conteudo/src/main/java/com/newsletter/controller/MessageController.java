package com.newsletter.controller;

import com.newsletter.dto.DelayedMessageRequest;
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

    /**
     * {
     * "targetDate": "2025-07-02 16:00:00",
     * "messageContent": "Lembrete: Reunião de equipe"
     * }
     */

    @PostMapping("/schedule-message")
    public ResponseEntity<String> scheduleMessage(@RequestBody DelayedMessageRequest request) {
        try {
            LocalDateTime targetDateTime = LocalDateTime.parse(request.getTargetDate(), DATE_FORMATTER);
            LocalDateTime currentDateTime = LocalDateTime.now();

            long delayMillis = targetDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() -
                    currentDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

            if (delayMillis < 0) {
                log.warn("Tentativa de agendar mensagem para o passado. Data alvo: {}", request.getTargetDate());
                return ResponseEntity.badRequest().body("A data alvo deve ser no futuro.");
            }

            messageProducer.sendMessageToDelayQueue(request.getMessageContent(), delayMillis);

            log.info("Mensagem '{}' agendada para ser processada em '{}' (atraso de {} ms)",
                    request.getMessageContent(), request.getTargetDate(), delayMillis);

            return ResponseEntity.ok("Mensagem '" + request.getMessageContent() +
                    "' agendada para " + request.getTargetDate() +
                    " (atraso de " + delayMillis + " ms).");

        } catch (DateTimeParseException e) {
            log.error("Erro ao parsear a data: {}. Formato esperado: yyyy-MM-dd HH:mm:ss", request.getTargetDate(), e);
            return ResponseEntity.badRequest().body("Formato de data inválido. Use 'yyyy-MM-dd HH:mm:ss'.");
        } catch (Exception e) {
            log.error("Erro inesperado ao agendar mensagem: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erro interno ao agendar mensagem.");
        }
    }
}