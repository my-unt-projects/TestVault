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
    private Long testCaseId;
    private String title;
    private String description;
    private String priority;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String assignedToEmail;
    private Long moduleId;
    private String moduleName;
    private List<Long> tagIds;
}
