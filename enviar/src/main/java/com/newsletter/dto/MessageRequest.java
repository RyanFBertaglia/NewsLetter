package com.newsletter.dto;

public record MessageRequest(
        String targetDate,
        String messageContent,
        String sendTo
) {}
