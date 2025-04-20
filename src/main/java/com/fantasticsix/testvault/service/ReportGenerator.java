package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.TestCase;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ReportGenerator {
    void generateReport(List<TestCase> testCases, OutputStream outputStream) throws IOException, DocumentException;
}