package com.newsletter.service;


import com.newsletter.model.MessageOpinion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class SentimentAnalysis {

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();


    public double analyzeSentiment(MessageOpinion request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());

            headers.setBearerAuth(apiToken);

            HttpEntity<Map<String, String>> entity =
                    new HttpEntity<>(Collections.singletonMap("inputs", request.message()), headers);

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
            return 5.0;
        }
    }


    private double convertToScore(List<Map<String, Object>> predictions) {
        if (predictions == null || predictions.isEmpty()) {
            return 5.0;
        }

        double total = 0;
        for (Map<String, Object> prediction : predictions) {
            String label = (String) prediction.get("label");
            double score = ((Number) prediction.get("score")).doubleValue();

            int stars = switch (label) {
                case "1 star" -> 1;
                case "2 stars" -> 2;
                case "4 stars" -> 4;
                case "5 stars" -> 5;
                default -> 3;
            };

            total += stars * score;
        }

        double averageStars = total;
        return ((averageStars - 1) / 4.0) * 10.0;
    }
}

