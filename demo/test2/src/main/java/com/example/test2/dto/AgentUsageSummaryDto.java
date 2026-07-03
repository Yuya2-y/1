package com.example.test2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentUsageSummaryDto {
    private String adoptedAgent;
    private Long count;
}
