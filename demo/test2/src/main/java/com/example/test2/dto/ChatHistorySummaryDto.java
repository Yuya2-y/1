package com.example.test2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatHistorySummaryDto {
    private String username;
    private long totalChats;
    private Double averageConfidence;
}
