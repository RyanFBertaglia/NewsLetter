package com.newsletter.infra;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // Nomes das filas e exchanges
    public static final String DELAY_QUEUE_NAME = "delay_queue";
    public static final String FINAL_QUEUE_NAME = "final_queue";
    public static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    public static final String FINAL_EXCHANGE_NAME = "final_exchange"; // Geralmente, o DLX é o final_exchange

    // Chave de roteamento para o DLX (pode ser o mesmo nome da fila final ou uma chave específica)
    public static final String DLX_ROUTING_KEY = "dlx.routing.key";

    /**
     * Declara o Dead Letter Exchange (DLX).
     * Este é um Direct Exchange que receberá mensagens expiradas.
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }

    /**
     * Declara a Fila de Processamento (Final Queue).
     * Esta é a fila que os consumidores ouvirão.
     */
    @Bean
    public Queue finalQueue() {
        return new Queue(FINAL_QUEUE_NAME);
    }

    /**
     * Vincula a Fila de Processamento ao DLX.
     * As mensagens do DLX serão roteadas para esta fila.
     */
    @Bean
    public Binding finalQueueBinding(Queue finalQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(finalQueue).to(dlxExchange).with(DLX_ROUTING_KEY);
    }

    /**
     * Declara a Fila de Atraso (Delay Queue).
     * Esta fila não será consumida diretamente.
     * As mensagens nela expirarão e serão enviadas para o DLX.
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        // Configura o Dead Letter Exchange para a Fila de Atraso
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        // Configura a chave de roteamento para as mensagens que forem para o DLX
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return new Queue(DELAY_QUEUE_NAME, true, false, false, args);
    }

    /**
     * Opcional: Se você quiser um exchange para a Fila de Atraso, mas geralmente
     * as mensagens são publicadas diretamente na fila de atraso sem um exchange
     * específico para ela, ou um DirectExchange default.
     * Para simplicidade, vamos publicar diretamente na fila de atraso.
     */
    /*
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    @Bean
    public Binding delayQueueBinding(Queue delayQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_QUEUE_NAME);
    }
    */
}

