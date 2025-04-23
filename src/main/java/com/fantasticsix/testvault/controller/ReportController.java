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
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/project-count")
    @ResponseBody
    public Map<String, Long> getProjectCounts() {
        List<Object[]> data = testCaseRepository.countTestCasesByProject();
        return data.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Long) obj[1]
                ));
    }

    @GetMapping("/creation-trend")
    @ResponseBody
    public Map<String, Long> getCreationTrend() {
        LocalDate today = LocalDate.now();
        Map<String, Long> result = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            Long count = testCaseRepository.countByCreationDate(Date.valueOf(date));
            result.put(date.toString(), count);
        }
        return result;
    }

    @GetMapping("/assigned-count")
    @ResponseBody
    public Map<String, Long> getAssignedCounts() {
        List<Object[]> data = testCaseRepository.countTestCasesPerUser();
        return data.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Long) obj[1]
                ));
    }

    @GetMapping("/upcoming-deadlines")
    @ResponseBody
    public List<TestCase> getUpcomingDeadlines() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return testCaseRepository.findByDueDateBetween(Date.valueOf(today), Date.valueOf(nextWeek));
    }

    @GetMapping("/status-priority-distribution")
    @ResponseBody
    public Map<String, Map<String, Long>> getStatusPriorityDistribution() {
        List<Object[]> data = testCaseRepository.countByStatusAndPriority();
        Map<String, Map<String, Long>> result = new HashMap<>();

        for (Object[] row : data) {
            String status = ((TestCase.Status) row[0]).name();
            String priority = ((TestCase.Priority) row[1]).name();
            Long count = (Long) row[2];

            result.computeIfAbsent(status, k -> new HashMap<>())
                    .put(priority, count);
        }

        return result;
    }
}