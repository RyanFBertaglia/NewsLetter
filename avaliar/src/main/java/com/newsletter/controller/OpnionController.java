package com.newsletter.controller;

import com.newsletter.model.MessageOpnion;
import com.newsletter.service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpnionController {

    private final SentimentAnalysis opnionService;

    @Autowired
    public OpnionController(SentimentAnalysis opnionService) {
        this.opnionService = opnionService;
    }

    @PostMapping("/sendComment")
    public ResponseEntity<String> sendComment(@RequestBody MessageOpnion message) {
        double rate = opnionService.analyzeSentiment(message);
        return ResponseEntity.ok(""+rate);
    }

}
