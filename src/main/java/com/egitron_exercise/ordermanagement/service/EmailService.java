package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.model.ErrorLog;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendErrorToUser(String to, String errorMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Error Report - Order Management Application");
        message.setText("Error processing your request:\n\n" + errorMessage);

        mailSender.send(message);
    }

    public void sendErrorReport(List<ErrorLog> logs, String to) {
        if (logs.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Error report\n\n");

        for (ErrorLog log : logs) {
            sb.append("[").append(log.getTimestamp()).append("] ")
                    .append(log.getMessage()).append("\n");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Error Report - Order Management Application");
        message.setText(sb.toString());

        mailSender.send(message);
    }

}
