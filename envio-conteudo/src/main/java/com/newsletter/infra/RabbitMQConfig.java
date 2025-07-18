package com.newsletter.infra;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String DELAY_QUEUE_NAME = "delay_queue";
    public static final String FINAL_QUEUE_NAME = "final_queue";
    public static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    public static final String DLX_ROUTING_KEY = "dlx.routing.key";


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }


    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }

    @Bean
    public Queue finalQueue() {
        return new Queue(FINAL_QUEUE_NAME);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Envio mensagens Processamento --> Final
    @Bean
    public Binding finalQueueBinding(Queue finalQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(finalQueue).to(dlxExchange).with(DLX_ROUTING_KEY);
    }

    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return new Queue(DELAY_QUEUE_NAME, true, false, false, args);
    }
}

