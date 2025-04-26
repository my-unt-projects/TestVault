package com.fantasticsix.testvault.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    @Column(unique = true, nullable = false)
    private String uuid;  // Unique UUID for the attachment

    @ManyToOne
    @JoinColumn(name = "test_case_id", nullable = true)
    private TestCase testCase;

    public Attachment() {
        this.uuid = UUID.randomUUID().toString();
    }
}