package com.newsletter.controller;

import com.newsletter.model.Rating;
import com.newsletter.services.CalculatorComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class RepercussaoController {

    private final CalculatorComment calculatorComment;

    @Autowired
    public RepercussaoController(CalculatorComment calculatorComment) {
        this.calculatorComment = calculatorComment;
    }

    @PostMapping("/getMedia")
    public ResponseEntity<String> getMedia(@RequestBody LocalDate dia) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/getAllMedias")
    public ResponseEntity<String> getAllMedias() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/novaMensagem")
    public ResponseEntity<String> enviarMensagem(@RequestBody Rating mensagem) {
        mensagem.setMedia(calculatorComment.calculate(mensagem.getComentario()));
        return ResponseEntity.ok("");
    }
}
