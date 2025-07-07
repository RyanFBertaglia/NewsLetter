package com.newsletter.validation;

import com.newsletter.infra.TestConfigurationSender;
import com.newsletter.controller.MessageController;
import com.newsletter.dto.DelayedMessageRequest;
import com.newsletter.service.MessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@Import(TestConfigurationSender.class)
class TestMessageController {

    private MockMvc mockMvc;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private MessageController messageController;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void scheduleMessage_ShouldReturnSuccess_WhenValidRequest() throws Exception {
        // Arrange
        String futureDate = LocalDateTime.now().plusHours(1).format(formatter);
        String requestBody = """
            {
                "targetDate": "%s",
                "messageContent": "Test message",
                "sendTo": "user@example.com"
            }
            """.formatted(futureDate);

        doNothing().when(messageProducer).sendDelayedMessage(any(DelayedMessageRequest.class), anyLong());

        // Act & Assert
        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Mensagem agendada para " + futureDate +
                        " para o destinatário user@example.com"));
    }

    @Test
    void scheduleMessage_ShouldReturnBadRequest_WhenDateIsInPast() throws Exception {
        // Arrange
        String pastDate = LocalDateTime.now().minusHours(1).format(formatter);
        String requestBody = """
            {
                "targetDate": "%s",
                "messageContent": "Test message",
                "sendTo": "user@example.com"
            }
            """.formatted(pastDate);

        // Act & Assert
        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A data alvo deve ser no futuro."));
    }

    @Test
    void scheduleMessage_ShouldReturnBadRequest_WhenInvalidDateFormat() throws Exception {
        // Arrange
        String requestBody = """
            {
                "targetDate": "2023/12/31 23:59:59",
                "messageContent": "Test message",
                "sendTo": "user@example.com"
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Formato de data inválido. Use 'yyyy-MM-dd HH:mm:ss'."));
    }

    @Test
    void scheduleMessage_ShouldReturnInternalServerError_WhenProducerFails() throws Exception {
        // Arrange
        String futureDate = LocalDateTime.now().plusHours(1).format(formatter);
        String requestBody = """
            {
                "targetDate": "%s",
                "messageContent": "Test message",
                "sendTo": "user@example.com"
            }
            """.formatted(futureDate);

        doThrow(new RuntimeException("Producer error"))
                .when(messageProducer)
                .sendDelayedMessage(any(DelayedMessageRequest.class), anyLong());

        // Act & Assert
        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro interno ao agendar mensagem."));
    }

    @Test
    void scheduleMessage_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
        // Arrange
        String requestBody = """
            {
                "targetDate": "2023-12-31 23:59:59"
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/schedule-message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}