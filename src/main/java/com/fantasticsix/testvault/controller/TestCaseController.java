package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.ModuleDto;
import com.fantasticsix.testvault.dto.TestCaseDto;
import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.Tag;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.service.*;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestCaseController {
    private final TestCaseService testCaseService;
    private final TagService tagService;
    private final UserService userService;
    private final ModuleService moduleService;
    private final ProjectService projectService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        TestCaseDto testCaseDto = new TestCaseDto();

        model.addAttribute("testCaseDto", testCaseDto);
        model.addAttribute("projects", projectService.getAllProjects());
//        model.addAttribute("modules", moduleService.getAll());

        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("tags", tagService.getAll()); // Add later
        return "tests/create";
    }



    @PostMapping("/create")
    public String createTestCase(@ModelAttribute("testCaseDto") TestCaseDto testCaseDto) {
        TestCase testCase = new TestCase();
        testCase.setTitle(testCaseDto.getTitle());
        testCase.setDescription(testCaseDto.getDescription());
        testCase.setPriority(testCaseDto.getPriority());
        testCase.setStatus(testCaseDto.getStatus());
        testCase.setCreationDate(testCaseDto.getCreationDate());
        testCase.setDueDate(testCaseDto.getDueDate());

        if (testCaseDto.getAssignedToEmail() != null) {
            Optional<User> assignedUser = userService.findByEmail(testCaseDto.getAssignedToEmail());
            testCase.setAssignedTo(assignedUser.orElse(null));
        }

        if (testCaseDto.getModuleId() != null) {
            testCase.setModule(moduleService.get(testCaseDto.getModuleId()));
        }

        if (testCaseDto.getProjectId() != null) {
            Project project = projectService.getProjectById(testCaseDto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            testCase.setProject(project);
        }

        if (testCaseDto.getTagIds() != null) {
            List<Tag> selectedTags = tagService.getAll().stream()
                    .filter(tag -> testCaseDto.getTagIds().contains(tag.getTagId()))
                    .toList();
            testCase.setTags(selectedTags);
        }

        testCaseService.save(testCase);
        return "redirect:/tests/all";
    }


    @GetMapping("/all")
    public String getAllTestCases(Model model) {
        List<TestCase> testCases = testCaseService.getAll();

        List<TestCaseDto> testCaseDtos = testCases.stream().map(testCase ->
                TestCaseDto.builder()
                        .title(testCase.getTitle())
                        .testCaseId(testCase.getTestCaseId())
                        .description(testCase.getDescription())
                        .priority(testCase.getPriority())
                        .status(testCase.getStatus())
                        .creationDate(testCase.getCreationDate())
                        .dueDate(testCase.getDueDate())
                        .assignedToEmail(testCase.getAssignedTo() != null ? testCase.getAssignedTo().getEmail() : null)
                        .moduleId(testCase.getModule() != null ? testCase.getModule().getModuleId() : null)
                        .moduleName(testCase.getModule() != null ? testCase.getModule().getModuleName() : null)
                        .tagIds(testCase.getTags() != null
                                ? testCase.getTags().stream().map(Tag::getTagId).toList()
                                : null)
                        .build()
        ).toList();

        model.addAttribute("testCases", testCaseDtos);
        return "tests/lists";
    }



//    @GetMapping("/module/{moduleId}")
//    public String listByModule(@PathVariable Long moduleId, Model model) {
//        List<TestCase> testCases = testCaseService.getByModuleId(moduleId);
//        Module module = moduleService.get(moduleId);
//        model.addAttribute("testCases", testCases);
//        model.addAttribute("module", module);
//        return "tests/list";
//    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        TestCase testCase = testCaseService.get(id);
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("testCase", testCase);
        model.addAttribute("users", users);
        return "tests/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateTestCase(@ModelAttribute TestCase testCase) {
        testCaseService.update(testCase);
        return "redirect:/tests/lists/" + testCase.getModule().getModuleId();
    }
}
