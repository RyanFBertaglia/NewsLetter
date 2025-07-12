package com.newsletter.controller;

import com.newsletter.model.MessageOpinion;
import com.newsletter.service.RateService;
import com.newsletter.service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpnionController {

    private final SentimentAnalysis opnionService;
    private final RateService rateService;

    @Autowired
    public OpnionController(SentimentAnalysis opnionService, RateService rateService) {
        this.opnionService = opnionService;
        this.rateService = rateService;
    }

    @PostMapping("/sendComment")
    public ResponseEntity<String> sendComment(@RequestBody MessageOpinion message, Long idUser) {

        rateService.verifyCopy(idUser, message.version());
        double rate = opnionService.analyzeSentiment(message);
        rateService.updateAverage(message.version(), rate);

        return ResponseEntity.ok("Thank you for answered this form");
    }

}
