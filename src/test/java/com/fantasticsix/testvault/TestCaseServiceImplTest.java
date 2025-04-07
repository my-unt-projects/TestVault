package com.fantasticsix.testvault;

import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.ModuleRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.impl.TestCaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestCaseServiceImplTest {

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private TestCaseServiceImpl testCaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTestCaseById() {
        TestCase testCase = new TestCase();
        testCase.setTestCaseId(1L);

        when(testCaseRepository.findById(1L)).thenReturn(Optional.of(testCase));

        TestCase result = testCaseService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTestCaseId());
    }

    @Test
    void shouldThrowExceptionWhenTestCaseNotFoundById() {
        when(testCaseRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            testCaseService.get(1L);
        });

        assertEquals("TestCase with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldSaveTestCase() {
        TestCase testCase = new TestCase();
        when(testCaseRepository.save(testCase)).thenReturn(testCase);

        TestCase saved = testCaseService.save(testCase);

        assertNotNull(saved);
        verify(testCaseRepository).save(testCase);
    }

    @Test
    void shouldDeleteTestCaseById() {
        Long id = 5L;
        testCaseService.delete(id);

        verify(testCaseRepository).deleteById(id);
    }

    @Test
    void shouldUpdateTestCase() {
        TestCase input = new TestCase();
        input.setTestCaseId(10L);
        input.setDescription("Updated description");

        TestCase existing = new TestCase();
        existing.setTestCaseId(10L);
        existing.setDescription("Old description");

        when(testCaseRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(testCaseRepository.save(any(TestCase.class))).thenAnswer(inv -> inv.getArgument(0));

        TestCase updated = testCaseService.update(input);

        assertEquals("Updated description", updated.getDescription());
        verify(testCaseRepository).save(existing);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTestCase() {
        TestCase input = new TestCase();
        input.setTestCaseId(99L);

        when(testCaseRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            testCaseService.update(input);
        });

        assertEquals("TestCase with id 99 not found", exception.getMessage());
    }

    @Test
    void shouldReturnTestCasesByModuleId() {
        Module module = new Module();
        module.setModuleId(3L);

        List<TestCase> testCases = List.of(new TestCase(), new TestCase());

        when(moduleRepository.findById(3L)).thenReturn(Optional.of(module));
        when(testCaseRepository.getTestCasesByModule(module)).thenReturn(testCases);

        List<TestCase> result = testCaseService.getByModuleId(3L);

        assertEquals(2, result.size());
    }

    @Test
    void shouldThrowExceptionWhenModuleNotFound() {
        when(moduleRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            testCaseService.getByModuleId(99L);
        });

        assertEquals("Module not found", exception.getMessage());
    }

    @Test
    void shouldReturnAllTestCases() {
        List<TestCase> allTestCases = List.of(new TestCase(), new TestCase(), new TestCase());

        when(testCaseRepository.findAll()).thenReturn(allTestCases);

        List<TestCase> result = testCaseService.getAll();

        assertEquals(3, result.size());
        verify(testCaseRepository).findAll();
    }
}
