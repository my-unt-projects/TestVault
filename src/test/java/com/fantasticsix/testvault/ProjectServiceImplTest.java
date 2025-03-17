package com.fantasticsix.testvault;

import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.ProjectRepository;
import com.fantasticsix.testvault.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private User projectManager;

    @BeforeEach
    void setUp() {
        projectManager = new User();
        projectManager.setId(100L);

        project = new Project();
        project.setId(1L);
        project.setProjectName("Test Project");
        project.setDescription("Project Description");
        project.setStartDate(new Date());
        project.setProjectManager(projectManager);
        project.setClientName("Test Client");
    }

    @Test
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));

        List<Project> projects = projectService.getAllProjects();

        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getProjectName());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById_Found() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<Project> foundProject = projectService.getProjectById(1L);

        assertTrue(foundProject.isPresent());
        assertEquals("Test Project", foundProject.get().getProjectName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Project> foundProject = projectService.getProjectById(1L);

        assertTrue(foundProject.isEmpty());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(project)).thenReturn(project);

        Project savedProject = projectService.createProject(project);

        assertNotNull(savedProject);
        assertEquals("Test Project", savedProject.getProjectName());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProject_Success() {
        Project updatedProjectDetails = new Project();
        updatedProjectDetails.setProjectName("Updated Project");
        updatedProjectDetails.setDescription("Updated Description");
        updatedProjectDetails.setStartDate(new Date());
        updatedProjectDetails.setProjectManager(projectManager);
        updatedProjectDetails.setClientName("Updated Client");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProjectDetails);

        Project result = projectService.updateProject(1L, updatedProjectDetails);

        assertNotNull(result);
        assertEquals("Updated Project", result.getProjectName());
        assertEquals("Updated Description", result.getDescription());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProject_NotFound() {
        Project updatedProjectDetails = new Project();
        updatedProjectDetails.setProjectName("Updated Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> projectService.updateProject(1L, updatedProjectDetails));

        assertEquals("Project not found", exception.getMessage());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProject() {
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }
}
