package com.fantasticsix.testvault.model;

import com.fantasticsix.testvault.enums.AttachmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    @ManyToOne
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;
}
