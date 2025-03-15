//package com.fantasticsix.testvault.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.util.Date;
//
//@Data
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long commentId;
//
//    @Column(nullable = false)
//    private String content;
//
//    @Column(nullable = false)
//    private String createdBy;
//
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date creationDate;
//
//    @ManyToOne
//    @JoinColumn(name = "test_case_id")
//    private TestCase testCase;
//}
