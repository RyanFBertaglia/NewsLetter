package com.newsletter.controller;

import com.newsletter.model.MessageOpinion;
import com.newsletter.service.RateService;
import com.newsletter.service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OpinionController {

    private final SentimentAnalysis opnionService;
    private final RateService rateService;

    @Autowired
    public OpinionController(SentimentAnalysis opnionService, RateService rateService) {
        this.opnionService = opnionService;
        this.rateService = rateService;
    }

    @PostMapping("/sendComment")
    public ResponseEntity<String> sendComment(@RequestBody MessageOpinion message,
                                              @RequestParam Long idUser) {

        rateService.verifyCopy(idUser, message.version());
        double rate = opnionService.analyzeSentiment(message);
        rateService.updateAverage(message.version(), rate);

        return ResponseEntity.ok("Rate: " + rate);
    }

}
