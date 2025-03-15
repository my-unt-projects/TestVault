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
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Notification {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long notificationId;
//
//    @Column(nullable = false)
//    private String message;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User sentTo;
//
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date sentDate;
//
//    @Column(nullable = false)
//    private Boolean isRead;
//}
