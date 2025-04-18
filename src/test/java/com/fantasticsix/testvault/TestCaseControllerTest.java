package com.fantasticsix.testvault;

import com.fantasticsix.testvault.controller.TestCaseController;
import com.fantasticsix.testvault.dto.TestCaseDto;
import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.Tag;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestCaseControllerTest {

    @InjectMocks
    private TestCaseController testCaseController;

    @Mock
    private TestCaseService testCaseService;

    @Mock
    private TagService tagService;

    @Mock
    private UserService userService;

    @Mock
    private ModuleService moduleService;

    @Mock
    private ProjectService projectService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        // optional setup
    }

    @Test
    void shouldDisplayCreateFormWithRequiredAttributes() {
        List<UserDto> users = List.of(new UserDto());
        List<Project> projects = List.of(new Project());
        List<Tag> tags = List.of(new Tag());

        when(userService.findAllUsers()).thenReturn(users);
        when(projectService.getAllProjects()).thenReturn(projects);
        when(tagService.getAll()).thenReturn(tags);

        String viewName = testCaseController.showCreateForm(model);

        verify(model).addAttribute(eq("testCaseDto"), any(TestCaseDto.class));
        verify(model).addAttribute("projects", projects);
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("tags", tags);
        assertEquals("tests/create", viewName);
    }

    @Test
    void shouldCreateTestCaseAndRedirect() {
        TestCaseDto dto = new TestCaseDto();
        dto.setTitle("Sample");
        dto.setDescription("Test Desc");
        dto.setPriority("High");
        dto.setStatus("Open");
        dto.setAssignedToEmail("user@example.com");
        dto.setModuleId(1L);
        dto.setProjectId(2L);
        dto.setTagIds(List.of(10L));

        User mockUser = new User();
        when(userService.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
        when(moduleService.get(1L)).thenReturn(null);
        Project mockProject = new Project();
        when(projectService.getProjectById(2L)).thenReturn(Optional.of(mockProject));
        Tag tag = new Tag();
        tag.setTagId(10L);
        when(tagService.getAll()).thenReturn(List.of(tag));

        String view = testCaseController.createTestCase(dto, Arrays.asList("1", "2"));

        verify(testCaseService).save(any(TestCase.class), any());
        assertEquals("redirect:/tests/all", view);
    }

    @Test
    void shouldReturnAllTestCasesView() {
        TestCase testCase = new TestCase();
        testCase.setTestCaseId(1L);
        testCase.setTitle("Test Title");
        testCase.setStatus("Open");
        testCase.setPriority("High");
        testCase.setDescription("Desc");
        testCase.setTags(List.of());

        when(testCaseService.getAll()).thenReturn(List.of(testCase));

        String view = testCaseController.getAllTestCases(model);

        verify(model).addAttribute(eq("testCases"), anyList());
        assertEquals("tests/lists", view);
    }


    @Test
    void shouldUpdateTestCaseAndRedirect() {
        TestCase testCase = new TestCase();
        com.fantasticsix.testvault.model.Module module = new com.fantasticsix.testvault.model.Module();
        module.setModuleId(5L);
        testCase.setModule(module);

        String view = testCaseController.updateTestCase(testCase);

        verify(testCaseService).update(testCase);
        assertEquals("redirect:/tests/lists/5", view);
    }
}