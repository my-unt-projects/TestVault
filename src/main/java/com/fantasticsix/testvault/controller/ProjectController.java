package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.constants.ProjectStatus;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.UserRepository;
import com.fantasticsix.testvault.service.ProjectService;
import com.fantasticsix.testvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    @GetMapping
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<User> managers = userRepository.findByRoleName("ROLE_ADMIN");
        model.addAttribute("project", new Project());
        model.addAttribute("statusOptions", ProjectStatus.values());
        model.addAttribute("managers", managers);
        return "projects/create";
    }

    @PostMapping
    public String createProject(@ModelAttribute Project project) {
        projectService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        List<User> managers = userRepository.findByRoleName("MANAGER"); // Fetch managers
        model.addAttribute("project", project);
        model.addAttribute("statusOptions", ProjectStatus.values());
        model.addAttribute("managers", managers); // Add managers to the model
        return "projects/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute Project project) {
        projectService.updateProject(id, project);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
