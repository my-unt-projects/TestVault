package com.fantasticsix.testvault;

import com.fantasticsix.testvault.controller.ReportController;
import com.fantasticsix.testvault.dto.PriorityCountDTO;
import com.fantasticsix.testvault.dto.StatusDateCountDTO;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.ReportGenerator;
import com.fantasticsix.testvault.service.TestCaseService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportControllerTest {

    @Mock
    private TestCaseService testCaseService;

    @Mock
    private ReportGenerator reportGenerator;

    @Mock
    private TestCaseRepository testCaseRepository;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateReport_ShouldGeneratePdf() throws IOException, DocumentException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        when(response.getOutputStream()).thenReturn(servletOutputStream);
        when(testCaseService.getAll()).thenReturn(Collections.emptyList());

        reportController.generateReport(response);

        verify(response).setContentType("application/pdf");
        verify(response).setHeader(eq("Content-Disposition"), contains("test-cases-report.pdf"));
        verify(reportGenerator).generateReport(anyList(), eq(servletOutputStream));
    }

    @Test
    void getChartData_ShouldReturnStatusDateCountList() {
        List<Object[]> mockData = Arrays.asList(
                new Object[]{"Open", "2024-04-01", 5L},
                new Object[]{"Closed", "2024-04-02", 3L}
        );
        when(testCaseRepository.countByStatusAndCreationDate()).thenReturn(mockData);

        List<StatusDateCountDTO> result = reportController.getChartData();

        assertEquals(2, result.size());
        assertEquals("Open", result.get(0).getStatus());
        assertEquals("2024-04-01", result.get(0).getCreationDate());
        assertEquals(5L, result.get(0).getCount());
    }

    @Test
    void getPriorityChartData_ShouldReturnPriorityCountList() {
        List<Object[]> mockData = Arrays.asList(
                new Object[]{"HIGH", 10L},
                new Object[]{"LOW", 5L}
        );
        when(testCaseRepository.countByPriority()).thenReturn(mockData);

        List<PriorityCountDTO> result = reportController.getPriorityChartData();

        assertEquals(2, result.size());
        assertEquals("HIGH", result.get(0).getPriority());
        assertEquals(10L, result.get(0).getCount());
    }

    @Test
    void getProjectCounts_ShouldReturnProjectCounts() {
        List<Object[]> mockData = Arrays.asList(
                new Object[]{"Project A", 4L},
                new Object[]{"Project B", 6L}
        );
        when(testCaseRepository.countTestCasesByProject()).thenReturn(mockData);

        Map<String, Long> result = reportController.getProjectCounts();

        assertEquals(2, result.size());
        assertEquals(4L, result.get("Project A"));
        assertEquals(6L, result.get("Project B"));
    }

    @Test
    void getCreationTrend_ShouldReturnLast7DaysTrend() {
        when(testCaseRepository.countByCreationDate(any(Date.class))).thenReturn(2L);

        Map<String, Long> result = reportController.getCreationTrend();

        assertEquals(7, result.size());
        result.values().forEach(count -> assertEquals(2L, count));
    }

    @Test
    void getAssignedCounts_ShouldReturnAssignedCounts() {
        List<Object[]> mockData = Arrays.asList(
                new Object[]{"User1", 5L},
                new Object[]{"User2", 8L}
        );
        when(testCaseRepository.countTestCasesPerUser()).thenReturn(mockData);

        Map<String, Long> result = reportController.getAssignedCounts();

        assertEquals(2, result.size());
        assertEquals(5L, result.get("User1"));
        assertEquals(8L, result.get("User2"));
    }

    @Test
    void getUpcomingDeadlines_ShouldReturnTestCases() {
        List<TestCase> mockTestCases = Arrays.asList(new TestCase(), new TestCase());
        when(testCaseRepository.findByDueDateBetween(any(Date.class), any(Date.class))).thenReturn(mockTestCases);

        List<TestCase> result = reportController.getUpcomingDeadlines();

        assertEquals(2, result.size());
    }

    @Test
    void getStatusPriorityDistribution_ShouldReturnDistributionMap() {
        TestCase.Status newStatus = TestCase.Status.NEW;
        TestCase.Priority highPriority = TestCase.Priority.HIGH;

        List<Object[]> mockData = Collections.singletonList(
                new Object[]{newStatus, highPriority, 7L}
        );
        when(testCaseRepository.countByStatusAndPriority()).thenReturn(mockData);

        Map<String, Map<String, Long>> result = reportController.getStatusPriorityDistribution();

        assertEquals(1, result.size());
        assertEquals(7L, result.get("NEW").get("HIGH"));
    }
}