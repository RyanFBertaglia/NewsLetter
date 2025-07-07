package com.newsletter.service;

import com.newsletter.model.DelayedMessageRequest;
import com.newsletter.infra.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDelayedMessage(DelayedMessageRequest messageRequest, long delayMillis) {
        log.info("Enviando mensagem para a fila de atraso: {} com atraso de {} ms", messageRequest, delayMillis);

        MessagePostProcessor messagePostProcessor = msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(delayMillis));
            return msg;
        };

        rabbitTemplate.convertAndSend(RabbitMQConfig.DELAY_QUEUE_NAME, messageRequest, messagePostProcessor);
    }
}