package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.repository.ModuleRepository;
import com.fantasticsix.testvault.repository.ProjectRepository;
import com.fantasticsix.testvault.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fantasticsix.testvault.model.Module;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Module addModule(com.fantasticsix.testvault.model.Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Module getModule(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module with id " + id + " not found"));
    }

    @Override
    public Module updateModule(Module module) {
        Module savedModule = moduleRepository.findById(module.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module with id " + module.getModuleId() + " not found"));
        savedModule.setModuleName(module.getModuleName());
        savedModule.setDescription(module.getDescription());
        savedModule.setTestCases(module.getTestCases());
        return moduleRepository.save(savedModule);
    }

    @Override
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public List<Module> getModulesByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project with id " + projectId + " not found"));
        return moduleRepository.getModulesByProject(project);
    }
}
