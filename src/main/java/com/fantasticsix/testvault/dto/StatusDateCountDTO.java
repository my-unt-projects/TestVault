package com.fantasticsix.testvault.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusDateCountDTO {
    private String status;
    private String creationDate;
    private Long count;
}
