package com.nur.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender javaMailSender() {

        String host = environment.getProperty("spring.mail.host");
        String port = environment.getProperty("spring.mail.port");
        String protocol = environment.getProperty("spring.mail.protocol");
        String encoding = environment.getProperty("spring.mail.default-encoding");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(StringUtils.isNoneBlank(port) ? port : "25"));
        mailSender.setProtocol(protocol);
        mailSender.setDefaultEncoding(encoding);

        mailSender.setUsername(environment.getProperty("spring.mail.username"));
        mailSender.setPassword(environment.getProperty("spring.mail.password"));

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", environment.getProperty("spring.mail.properties.mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }
}
