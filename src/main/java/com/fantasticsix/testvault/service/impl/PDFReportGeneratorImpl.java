package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.service.ReportGenerator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PDFReportGeneratorImpl implements ReportGenerator {
    @Override
    public void generateReport(List<TestCase> testCases, OutputStream outputStream) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();
            document.addTitle("Test Cases Report");

            // Add header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph header = new Paragraph("Test Cases Report", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(20);
            document.add(header);

            // Create table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            // Add table headers
            Stream.of("Title", "Priority", "Status", "Module", "Assignee")
                    .forEach(columnTitle -> {
                        PdfPCell headerCell = new PdfPCell();
                        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        headerCell.setBorderWidth(2);
                        headerCell.setPhrase(new Phrase(columnTitle));
                        headerCell.setPadding(5);
                        table.addCell(headerCell);
                    });

            // Add data rows
            for (TestCase testCase : testCases) {
                table.addCell(testCase.getTitle());
                table.addCell(testCase.getPriority().toString());
                table.addCell(testCase.getStatus().toString());
                table.addCell(testCase.getModule() != null ? testCase.getModule().getModuleName() : "N/A");
                table.addCell(testCase.getAssignedTo() != null ? testCase.getAssignedTo().getEmail() : "Unassigned");
            }

            document.add(table);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}