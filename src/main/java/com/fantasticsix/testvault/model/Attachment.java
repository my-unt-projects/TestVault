//package com.fantasticsix.testvault.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Attachment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long attachmentId;
//
//    @Column(nullable = false)
//    private String filePath;
//
//    @Column(nullable = false)
//    private String type;
//
//    @ManyToOne
//    @JoinColumn(name = "test_case_id")
//    private TestCase testCase;
//}
