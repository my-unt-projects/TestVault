package com.fantasticsix.testvault.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCaseDto {
    private String title;
    private String description;
    private String priority;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private Long assignedToId;
    private Long moduleId;
    private List<Long> tagIds;
}
