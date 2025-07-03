package com.newsletter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayedMessageRequest {
    private String targetDate;
    private String messageContent;
}
