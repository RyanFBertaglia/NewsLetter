package com.newsletter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Objeto de Requisição para mensagens com atraso.
 * Contém a data alvo para processamento e o conteúdo da mensagem.
 */
@Data // Gera getters, setters, toString, equals e hashCode automaticamente (Lombok)
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class DelayedMessageRequest {

    /**
     * A data alvo em que a mensagem deve ser processada.
     * Espera-se um formato de string que possa ser parseado (ex: "yyyy-MM-dd HH:mm:ss").
     */
    private String targetDate;

    /**
     * O conteúdo real da mensagem que será enviado e processado.
     */
    private String messageContent;
}
