package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(Long id);

    Project createProject(Project project);

    Project updateProject(Long id, Project projectDetails);

    void deleteProject(Long id);
}
