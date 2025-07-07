package com.newsletter.service;

import com.newsletter.model.MessageRequest;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SenderService {

    private static final Logger logger = LoggerFactory.getLogger(SenderService.class);
    private static final String CONTENT_TYPE = "text/plain";
    private static final String SEND_ENDPOINT = "mail/send";

    private final String senderEmail;
    private final SendGrid sendGridClient;
    private final UserService userService;

    @Autowired
    public SenderService(
            @Value("${sender.email}") String senderEmail,
            @Value("${SENDGRID_API_KEY}") String apiKey,
            UserService userService) {
        this.senderEmail = senderEmail;
        this.sendGridClient = new SendGrid(apiKey);
        this.userService = userService;
    }

    public void send(String toEmail, String message) {
        try {
            sendEmail(toEmail, message);
        } catch (IOException e) {
            logger.error("Falha ao enviar e-mail para {}", toEmail, e);
        }
    }

    public void broadcast(MessageRequest message) {
        List<String> toEmails = userService.getUser();
        toEmails.forEach(toEmail -> send(toEmail, message.messageContent()));
    }

    private void sendEmail(String recipient, String messageBody) throws IOException {
        Email from = new Email(this.senderEmail);
        Email to = new Email(recipient);
        Content content = new Content(CONTENT_TYPE, messageBody);
        Mail mail = new Mail(from, "NewsLetter", to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(SEND_ENDPOINT);
        request.setBody(mail.build());

        Response response = sendGridClient.api(request);
        logResponse(response);
    }

    private void logResponse(Response response) {
        logger.info("Status Code: {}", response.getStatusCode());
        logger.debug("Response Body: {}", response.getBody());
        logger.debug("Headers: {}", response.getHeaders());
    }
}