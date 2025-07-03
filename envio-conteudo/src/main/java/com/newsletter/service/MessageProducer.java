package com.newsletter.service;

import com.newsletter.infra.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToDelayQueue(String message, long delayMillis) {
        log.info("Enviando mensagem para a fila de atraso: '{}' com atraso de {} ms", message, delayMillis);

        MessagePostProcessor messagePostProcessor = msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(delayMillis));
            return msg;
        };
        // Publicamos a mensagem diretamente na fila de atraso (sem um exchange específico para ela).
        // Se você tivesse um exchange para a delay_queue, usaria rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message, postProcessor);
        rabbitTemplate.convertAndSend(RabbitMQConfig.DELAY_QUEUE_NAME, (Object) message, messagePostProcessor);
    }
}
