package com.newsletter.controller;

import com.newsletter.exception.UpdateFailedException;
import com.newsletter.model.MessageOpinion;
import com.newsletter.model.Rate;
import com.newsletter.service.RateService;
import com.newsletter.service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://frontend:3000"})
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

    @GetMapping("/getVersions")
    public ResponseEntity<List<Long>> getVersions() {
        return ResponseEntity.ok(rateService.getVersions());
    }

    @GetMapping("/getRate")
    public ResponseEntity<Rate> getRate(@RequestParam Long version) {
        return ResponseEntity.ok(rateService.getRate(version));
    }

    @PostMapping("/newRate/{version}")
    public ResponseEntity<Rate> newRate(@PathVariable Long version) {
        return ResponseEntity.ok(rateService.newRate(version));
    }

    @PutMapping("/updateAverage/{version}/{rate}")
    public ResponseEntity<Rate> updateAverage(@PathVariable Long version, @PathVariable double rate) {
        if(rate > 10 || rate < 0) { throw new UpdateFailedException("Insert a valid rate"); }
        rateService.updateAverage(version, rate);
        return ResponseEntity.ok(rateService.getRate(version));
    }

}
