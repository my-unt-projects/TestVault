package com.fantasticsix.testvault.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testCaseId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    @JsonIgnore
    private User assignedTo;

    @ManyToMany
    @JoinTable(
            name = "testcase_tags",
            joinColumns = @JoinColumn(name = "test_case_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnore
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = true)
    @JsonIgnore
    private Module module;

    @ManyToOne
    @JoinColumn(name="project_id")
    @JsonIgnore
    private Project project;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public enum Status {
        NEW, IN_PROGRESS, COMPLETED, ON_HOLD, TODO, DONE
    }


    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }
}
