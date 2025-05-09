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
}
