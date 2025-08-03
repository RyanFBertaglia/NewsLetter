package com.newsletter;

import com.newsletter.model.UsersRepository;
import com.newsletter.service.SenderService;
import com.newsletter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = {SenderService.class, UserService.class})
@EnableSpringConfigured
public class TestSenderEmail {

    @MockitoBean
    private UsersRepository usersRepository;

    private final SenderService senderService;

    @Autowired
    public TestSenderEmail(
            @Value("${sender.email}") String senderEmail,
            @Value("${SENDGRID_API_KEY}") String apiKey,
            UserService userService) {
        this.senderService = new SenderService(senderEmail, apiKey, userService);
    }

    @Test
    public void testSend() {
        senderService.send("oderleibertaglia12@gmail.com", "Qualquer mensagem");
    }
}
