package com.newsletter.controller;

import com.newsletter.dto.RatingDatailsDTO;
import com.newsletter.model.feedback.Rate;
import com.newsletter.dto.RateAverageDTO;
import com.newsletter.dto.UserShareDTO;
import com.newsletter.service.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RepercussaoController {

    private final Statistics statistics;

    @Autowired
    public RepercussaoController(Statistics statistics) {
        this.statistics = statistics;
    }

    @PostMapping("/getAverage/{version}")
    public ResponseEntity<Double> getAverageByVersion(@PathVariable Long version) {
        return ResponseEntity.ok(statistics.findAverage(version));
    }

    @PostMapping("/getAverageOnDay")
    public ResponseEntity<List<RateAverageDTO>> getMediaByDay(@RequestBody LocalDate date) {
        return ResponseEntity.ok(statistics.findAverageByDay(date));
    }

    @PostMapping("/getAllMedias")
    public ResponseEntity<List<Rate>> getAllMedias() {
        return ResponseEntity.ok(statistics.getAllRatings());
    }

    @PostMapping("/getShares")
    public ResponseEntity<List<UserShareDTO>> getAllShares() {
        return ResponseEntity.ok(statistics.getShares());
    }

    @GetMapping("/getPostInfo/{version}")
    public ResponseEntity<RatingDatailsDTO> getPostDatails(@PathVariable Long version) {
        RatingDatailsDTO postDetails = statistics.getRatingDatails(version);
        return ResponseEntity.ok(postDetails);
    }

}
