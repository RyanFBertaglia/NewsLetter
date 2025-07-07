package com.newsletter.model;

public record MessageRequest(
        String targetDate,
        String messageContent,
        String sendTo
) {}
