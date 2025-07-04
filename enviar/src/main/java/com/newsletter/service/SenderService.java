package com.newsletter.service;

import com.newsletter.dto.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {
    @Autowired
    public SenderService() {
    }

    public void send(String email, MessageRequest message) {
    }

    public void broadcast(MessageRequest message) {
    }

}
