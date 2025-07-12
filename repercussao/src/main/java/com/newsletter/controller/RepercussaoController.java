package com.newsletter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class RepercussaoController {

    @PostMapping("/getMedia")
    public ResponseEntity<String> getMedia(@RequestBody LocalDate dia) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/getAllMedias")
    public ResponseEntity<String> getAllMedias() {
        return ResponseEntity.ok("");
    }

}
