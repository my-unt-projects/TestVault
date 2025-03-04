package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.repository.ProjectRepository;
import com.fantasticsix.testvault.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setProjectName(projectDetails.getProjectName());
        project.setDescription(projectDetails.getDescription());
        project.setStartDate(projectDetails.getStartDate());
        project.setProjectManager(projectDetails.getProjectManager());
        project.setStatus(projectDetails.getStatus());
        project.setClientName(projectDetails.getClientName());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
