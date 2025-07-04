package com.newsletter.dto;

public record DelayedMessageRequest(
        String targetDate,
        String messageContent,
        String sendTo
) {}
