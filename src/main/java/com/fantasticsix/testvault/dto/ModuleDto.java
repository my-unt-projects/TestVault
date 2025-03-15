package com.fantasticsix.testvault.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleDto {
    private Long moduleId;
    private String moduleName;
    private String description;
    private Long projectId;
    private String projectName;
}
