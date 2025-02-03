package com.nur.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

//    @Async
//    public void sendSimpleEmail(String to, String subject, String message) {
//        var mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(to);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//        mailSender.send(mailMessage);
//    }

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    // Example method using Text Blocks for better readability
    public String getHtmlEmailContent(String name, String country, String ratingDate) {
        return """
                <html>
                <body>
                    <h2>Hello %s,</h2>
                    <p>A new rating action has been added for <b>%s</b> on <b>%s</b>.</p>
                    <p>Thank you!</p>
                </body>
                </html>
                """.formatted(name, country, ratingDate);
    }
}
