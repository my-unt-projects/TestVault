package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.service.ReportGenerator;
import com.fantasticsix.testvault.service.TestCaseService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {
    private final TestCaseService testCaseService;
    private final ReportGenerator reportGenerator;

    @Autowired
    public ReportController(TestCaseService testCaseService, ReportGenerator reportGenerator) {
        this.testCaseService = testCaseService;
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/generate")
    public void generateReport(HttpServletResponse response) throws IOException, DocumentException {
        List<TestCase> testCases = testCaseService.getAll();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=test-cases-report.pdf");
        reportGenerator.generateReport(testCases, response.getOutputStream());
    }
}