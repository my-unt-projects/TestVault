package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.PriorityCountDTO;
import com.fantasticsix.testvault.dto.StatusDateCountDTO;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.ReportGenerator;
import com.fantasticsix.testvault.service.TestCaseService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final TestCaseService testCaseService;
    private final ReportGenerator reportGenerator;
    private final TestCaseRepository testCaseRepository;


    @GetMapping("/generate")
    public void generateReport(HttpServletResponse response) throws IOException, DocumentException {
        List<TestCase> testCases = testCaseService.getAll();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=test-cases-report.pdf");
        reportGenerator.generateReport(testCases, response.getOutputStream());
    }

    @GetMapping("/chart-data")
    @ResponseBody
    public List<StatusDateCountDTO> getChartData() {
        List<Object[]> rawData = testCaseRepository.countByStatusAndCreationDate();
        return rawData.stream().map(obj ->
                new StatusDateCountDTO(
                        obj[0].toString(),
                        obj[1].toString(),
                        ((Number) obj[2]).longValue()
                )
        ).collect(Collectors.toList());
    }

    @GetMapping("/priority-chart-data")
    @ResponseBody
    public List<PriorityCountDTO> getPriorityChartData() {
        List<Object[]> rawData = testCaseRepository.countByPriority();
        return rawData.stream()
                .map(obj -> new PriorityCountDTO(obj[0].toString(), ((Number)obj[1]).longValue()))
                .collect(Collectors.toList());
    }
}