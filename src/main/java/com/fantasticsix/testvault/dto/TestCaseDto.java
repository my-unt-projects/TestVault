package com.fantasticsix.testvault.dto;

import com.fantasticsix.testvault.model.Project;
import lombok.*;
import org.hibernate.event.spi.PreInsertEvent;
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
    private Long projectId;
    private String moduleName;
    private List<Long> tagIds;
    private List<String> attachments;

    @Override
    public String toString() {
        return "TestCaseDto{" +
                "testCaseId=" + testCaseId +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
