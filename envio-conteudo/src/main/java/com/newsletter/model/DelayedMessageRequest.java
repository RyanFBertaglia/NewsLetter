package com.newsletter.model;

public record DelayedMessageRequest(
        String targetDate,
        String messageContent,
        String sendTo
) {}
