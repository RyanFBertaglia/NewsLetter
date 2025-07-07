package com.newsletter.controller;

import com.newsletter.infra.QueueConfig;
import com.newsletter.model.MessageRequest;
import com.newsletter.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final SenderService senderService;

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    public MessageConsumer(SenderService senderService) {
        this.senderService = senderService;
    }

    @RabbitListener(queues = QueueConfig.FINAL_QUEUE_NAME)
    public void receiveMessage(MessageRequest message) {
        log.info("Mensagem recebida da fila final: '{}' às {}", message, System.currentTimeMillis());
        if(message.sendTo().equals("*")) {
            senderService.broadcast(message);
        } else{
            senderService.send(message.sendTo(), message.messageContent());
        }
    }
}
