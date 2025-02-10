package com.nur.service;

import com.nur.dto.EmailDTO;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(EmailDTO emailDTO) {
        LOGGER.info("Sending email to: {}", emailDTO.getEmailTo());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailDTO.getEmailTo().split(","));
            helper.setFrom(emailDTO.getEmailFrom());
            helper.setSubject(emailDTO.getEmailSubject());
            helper.setText(emailDTO.getEmailBody() + "\n\n" + emailDTO.getEmailSignature(), true);
            mailSender.send(message);
            LOGGER.info("Email sent successfully!");
            //return true;
        } catch (Exception e) {
            LOGGER.error("Error sending email: {}", e.getMessage());
            //return false;
        }
    }

}