package com.fantasticsix.testvault;

import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.ModuleRepository;
import com.fantasticsix.testvault.repository.ProjectRepository;
import com.fantasticsix.testvault.service.impl.ModuleServiceImpl;
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
class ModuleServiceImplTest {

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ModuleServiceImpl moduleService;

    private Module module;
    private Project project;

    @BeforeEach
    void setUp() {

        User projectManager = new User();
        projectManager.setId(100L);

        project = new Project();
        project.setId(1L);
        project.setProjectName("Test Project");
        project.setDescription("Project Description");
        project.setStartDate(new Date());
        project.setProjectManager(projectManager);
        project.setClientName("Test Client");

        module = new Module();
        module.setModuleId(1L);
        module.setModuleName("Test Module");
        module.setDescription("Module Description");
        module.setProject(project);
    }

    @Test
    void testAddModule() {
        when(moduleRepository.save(module)).thenReturn(module);

        Module savedModule = moduleService.add(module);

        assertNotNull(savedModule);
        assertEquals("Test Module", savedModule.getModuleName());
        assertEquals(project, savedModule.getProject());
        verify(moduleRepository, times(1)).save(module);
    }

    @Test
    void testGetModuleById() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

        Module foundModule = moduleService.get(1L);

        assertNotNull(foundModule);
        assertEquals(1L, foundModule.getModuleId());
        verify(moduleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetModuleById_NotFound() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> moduleService.get(1L));

        assertEquals("Module with id 1 not found", exception.getMessage());
        verify(moduleRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateModule() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));
        when(moduleRepository.save(any(Module.class))).thenReturn(module);

        Module updatedModule = new Module();
        updatedModule.setModuleId(1L);
        updatedModule.setModuleName("Updated Module");
        updatedModule.setDescription("Updated Description");

        Module result = moduleService.update(updatedModule);

        assertNotNull(result);
        assertEquals("Updated Module", result.getModuleName());
        assertEquals("Updated Description", result.getDescription());
        verify(moduleRepository, times(1)).findById(1L);
        verify(moduleRepository, times(1)).save(any(Module.class));
    }

    @Test
    void testUpdateModule_NotFound() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> moduleService.update(module));

        assertEquals("Module with id 1 not found", exception.getMessage());
        verify(moduleRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteModule() {
        doNothing().when(moduleRepository).deleteById(1L);

        moduleService.delete(1L);

        verify(moduleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllModulesByProjectId() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(moduleRepository.getModulesByProject(project)).thenReturn(Arrays.asList(module));

        List<Module> modules = moduleService.getAllByProjectId(1L);

        assertFalse(modules.isEmpty());
        assertEquals(1, modules.size());
        assertEquals("Test Module", modules.get(0).getModuleName());
        verify(projectRepository, times(1)).findById(1L);
        verify(moduleRepository, times(1)).getModulesByProject(project);
    }

    @Test
    void testGetAllModulesByProjectId_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> moduleService.getAllByProjectId(1L));

        assertEquals("Project with id 1 not found", exception.getMessage());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllModules() {
        when(moduleRepository.findAll()).thenReturn(Arrays.asList(module));

        List<Module> modules = moduleService.getAll();

        assertFalse(modules.isEmpty());
        assertEquals(1, modules.size());
        verify(moduleRepository, times(1)).findAll();
    }
}
