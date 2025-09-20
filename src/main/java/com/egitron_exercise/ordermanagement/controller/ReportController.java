package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.service.EmailService;
import com.egitron_exercise.ordermanagement.service.ErrorLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ErrorLogService errorLogService;
    private final EmailService emailService;

    @Value("${app.report.mail-to:fredericosoliz@gmail.com}")
    private String reportMailTo;

    public ReportController(ErrorLogService errorLogService, EmailService emailService) {
        this.errorLogService = errorLogService;
        this.emailService = emailService;
    }

    // all the errors
    @GetMapping("/errors/all")
    public String sendAllErrorsReport() {
        var logs = errorLogService.findAll();
        emailService.sendErrorReport(logs, reportMailTo);
        return "Send error report to " + reportMailTo;
    }

    // Last day errors (24h)
    @GetMapping("/errors/today")
    public String sendTodayErrorsReport() {
        var logs = errorLogService.findSince(LocalDateTime.now().minusDays(1));
        emailService.sendErrorReport(logs, reportMailTo);
        return "Send today's error report to " + reportMailTo;
    }
}
