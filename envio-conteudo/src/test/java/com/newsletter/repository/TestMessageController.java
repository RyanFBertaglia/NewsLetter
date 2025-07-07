package com.newsletter.repository;

// import com.newsletter.infra.TestConfigurationSender; // Removido para simplificar o contexto de teste
import com.newsletter.controller.MessageController; // Mantido, pois o controlador faz parte do teste
import com.newsletter.dto.DelayedMessageRequest;
import com.newsletter.infra.TestConfigurationSender;
import com.newsletter.service.MessageProducer; // Importe a classe real
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach; // Removido, pois MockMvc é injetado automaticamente
// import org.junit.jupiter.api.extension.ExtendWith; // Removido se não houver outras extensões necessárias
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Nova anotação
import org.springframework.boot.test.context.SpringBootTest; // Nova anotação
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders; // Não será mais usado com @SpringBootTest

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // Importe esta linha

// Remova @ExtendWith(MockitoExtension.class) se não houver outros mocks
// @ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfigurationSender.class)
class TestMessageController {

    @Autowired
    private MockMvc mockMvc;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String json(String date, String msg, String to) {
        return """
            {
              "targetDate": "%s",
              "messageContent": "%s",
              "sendTo": "%s"
            }
            """.formatted(date, msg, to);
    }

    @Test
    void deveAgendarMensagem() throws Exception {
        var dataFutura = LocalDateTime.now().plusMinutes(1).format(FORMATTER);

        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(dataFutura, "Olá!", "email@teste.com"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Mensagem agendada para " + dataFutura + " para o destinatário email@teste.com"));
    }

    @Test
    void deveRejeitarDataPassada() throws Exception {
        var dataPassada = LocalDateTime.now().minusHours(1).format(FORMATTER);

        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(dataPassada, "Mensagem", "email@teste.com"))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A data alvo deve ser no futuro."));
    }

    @Test
    void deveRejeitarFormatoDataInvalido() throws Exception {
        var body = json("2025/01/01 10:00:00", "Mensagem", "email@teste.com");

        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Formato de data inválido. Use 'yyyy-MM-dd HH:mm:ss'."));
    }

    @Test
    void deveRetornarBadRequestParaCamposFaltando() throws Exception {
        var body = """
            {
              "targetDate": "2025-01-01 10:00:00"
            }
            """;

        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}
