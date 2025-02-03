package com.nur.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "crr_email_notification")
@Data
public class CrrEmailNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailSubject;
    private String emailBody;
    private String emailFrom;
    private String emailTo;
    private String emailSignature;
}
