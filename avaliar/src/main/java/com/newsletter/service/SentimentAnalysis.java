package com.newsletter.service;


import com.newsletter.model.MessageOpinion;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


//@Slf4j
@Service
public class SentimentAnalysis {


    private static final Logger log = LoggerFactory.getLogger(SentimentAnalysis.class);




    @Value("${huggingface.api.url}")
    private String apiUrl;


    @Value("${huggingface.api.token}")
    private String apiToken;


    private final RestTemplate restTemplate = new RestTemplate();


    public double analyzeSentiment(MessageOpinion request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
            headers.setBearerAuth(apiToken);


            HttpEntity<Map<String, String>> entity = new HttpEntity<>(
                    Collections.singletonMap("inputs", request.message()), headers
            );


            ResponseEntity<List<List<Map<String, Object>>>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );


            List<Map<String, Object>> predictions = response.getBody() != null && !response.getBody().isEmpty()
                    ? response.getBody().get(0)
                    : Collections.emptyList();


            return convertToScore(predictions);


        } catch (Exception e) {
            log.info("Erro while analysis: {}", String.valueOf(e.getCause()));
            return 5.0;
        }
    }


    private double convertToScore(List<Map<String, Object>> predictions) {
        if (predictions == null || predictions.isEmpty()) {
            log.info("Resposta nula");
            return 5.0;
        }


        Map<String, Object> bestPrediction = predictions.stream()
                .max(Comparator.comparingDouble(p -> ((Number) p.get("score")).doubleValue()))
                .orElse(null);




        String label = (String) bestPrediction.get("label");
        double score = ((Number) bestPrediction.get("score")).doubleValue();


        log.info(label);


        return switch (label) {
            case "1 star" -> 0.0 + (score * 2);  // 0 a 2
            case "2 stars" -> 2.0 + (score * 2); // 2 a 4
            case "3 stars" -> 4.0 + (score * 2); // 4 a 6
            case "4 stars" -> 6.0 + (score * 2); // 6 a 8
            case "5 stars" -> 8.0 + (score * 2); // 8 a 10
            default -> 5.0;
        };
    }
}

