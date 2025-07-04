package com.newsletter.controller;

import com.newsletter.infra.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    /**
     * Este método é um listener que será invocado sempre que uma nova mensagem
     * estiver disponível na Fila de Processamento Final (FINAL_QUEUE_NAME).
     * As mensagens só chegam aqui após terem expirado na Fila de Atraso e
     * terem sido roteadas pelo Dead Letter Exchange (DLX).
     *
     * @param messageContent O conteúdo da mensagem recebida (neste caso, uma string).
     */
    @RabbitListener(queues = QueueConfig.FINAL_QUEUE_NAME)
    public void receiveMessage(String messageContent) {
        // Registra a mensagem recebida e o timestamp atual.
        log.info("Mensagem recebida da fila final: '{}' às {}", messageContent, System.currentTimeMillis());

        // --- Lógica para enviar os dados para um serviço não desenvolvido ---
        // Aqui, você chamaria um método de um serviço (que não será implementado aqui).
        // Por exemplo:
        // myUndevelopedService.processFinalMessage(messageContent);
        //
        // Para simular, vamos apenas imprimir que estamos enviando para o serviço.
        //sendToUndevelopedService(messageContent);
    }
}
