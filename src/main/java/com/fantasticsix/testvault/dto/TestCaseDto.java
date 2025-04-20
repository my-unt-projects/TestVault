package com.fantasticsix.testvault.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private Long testCaseId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Date creationDate;
    private Date dueDate;
    private String assignedToEmail;
    private Long moduleId;
    private String moduleName;
    private Long projectId;
    private List<Long> tagIds;
}