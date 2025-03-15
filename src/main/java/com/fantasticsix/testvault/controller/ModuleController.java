package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.ModuleDto;
import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.service.ModuleService;
import com.fantasticsix.testvault.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/modules")
@RequiredArgsConstructor
@Slf4j
public class ModuleController {

    private final ModuleService moduleService;
    private final ProjectService projectService;

    @GetMapping
    public String getAllModules(Model model) {
        log.info(">>>>>>>>>>>>>>>>>> Get all modules invoked <<<<<<<<<<<<<<<<<");
        List<ModuleDto> modules = moduleService.getAll()
                .stream()
                .map(module -> ModuleDto.builder()
                        .moduleId(module.getModuleId())
                        .description(module.getDescription())
                        .moduleName(module.getModuleName())
                        .projectId(module.getProject().getId())
                        .projectName(module.getProject().getProjectName())
                        .build()
                )
                .collect(Collectors.toList());

        model.addAttribute("modules", modules);
        return "modules/list";
    }


    @GetMapping("/new")
    public String showCreateModuleForm(Model model) {
        log.info("=====>>>>>>> /new module form <<<<<<<<<<<<<<<<");
        model.addAttribute("module", new ModuleDto());

        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);

        return "modules/create";
    }

    @PostMapping
    public String createModule(@ModelAttribute ModuleDto moduleRequestDto) {
        Project project = projectService.getProjectById(moduleRequestDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Module module = new Module();
        module.setModuleName(moduleRequestDto.getModuleName());
        module.setDescription(moduleRequestDto.getDescription());
        module.setProject(project);
        module.setModuleId(moduleRequestDto.getModuleId());

        Module savedModule = moduleService.add(module);
        return "redirect:/modules";
    }


}
