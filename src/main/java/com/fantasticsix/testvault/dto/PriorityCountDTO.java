package com.fantasticsix.testvault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriorityCountDTO {
    private String priority;
    private Long count;
}