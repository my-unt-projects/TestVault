package com.fantasticsix.testvault.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachmentDto {
    private String uuid;
    private String name;
    private String fileType;
    private String filePath;

    public AttachmentDto(String uuid, String name, String fileType, String filePath) {
        this.uuid = uuid;
        this.name = name;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
