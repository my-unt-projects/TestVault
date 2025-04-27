package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.TestCaseDto;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.Tag;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.*;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@lombok.extern.slf4j.Slf4j
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
    private final TestCaseRepository testCaseRepository;
    private final EmailService emailService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        TestCaseDto testCaseDto = new TestCaseDto();

        model.addAttribute("testCaseDto", testCaseDto);
        model.addAttribute("projects", projectService.getAllProjects());

        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("tags", tagService.getAll()); // Add later
        return "tests/create";
    }

    private String getLoggedInUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PostMapping("/create")
    public String createTestCase(@ModelAttribute("testCaseDto") TestCaseDto testCaseDto,
                                 @RequestParam List<String> attachmentUuids, HttpServletRequest request) {
        TestCase testCase = new TestCase();
        testCase.setTitle(testCaseDto.getTitle());
        testCase.setDescription(testCaseDto.getDescription());
        testCase.setPriority(TestCase.Priority.valueOf(testCaseDto.getPriority()));
        testCase.setStatus(TestCase.Status.valueOf(testCaseDto.getStatus()));
        testCase.setDueDate(testCaseDto.getDueDate());

        String loggedInUserEmail = getLoggedInUserEmail();


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

        if (testCaseDto.getAssignedToEmail() != null) {
            Optional<User> assignedUser = userService.findByEmail(testCaseDto.getAssignedToEmail());
            assignedUser.ifPresent(user -> {
                testCase.setAssignedTo(user);

                if (!user.getEmail().equalsIgnoreCase(loggedInUserEmail)) {
                    try {
                        emailService.sendAssignmentNotification(user, testCase, request);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            });
        }

        testCaseService.save(testCase, attachmentUuids);


        return "redirect:/tests/all";
    }

    @PostMapping("/update")
    public String updateTestCase(@ModelAttribute("testCaseDto") TestCaseDto testCaseDto,
                                 @RequestParam List<String> attachmentUuids, HttpServletRequest request) {
        TestCase existingTestCase = testCaseRepository.findById(testCaseDto.getTestCaseId()).orElseThrow(() -> new IllegalArgumentException("Invalid test case"));

        existingTestCase.setTitle(testCaseDto.getTitle());
        existingTestCase.setDescription(testCaseDto.getDescription());
        existingTestCase.setPriority(TestCase.Priority.valueOf(testCaseDto.getPriority()));
        existingTestCase.setStatus(TestCase.Status.valueOf(testCaseDto.getStatus()));
        existingTestCase.setDueDate(testCaseDto.getDueDate());

        String loggedInUserEmail = getLoggedInUserEmail();
        User previousAssignee = existingTestCase.getAssignedTo();

        if (testCaseDto.getAssignedToEmail() != null) {
            Optional<User> newAssigneeOpt = userService.findByEmail(testCaseDto.getAssignedToEmail());
            newAssigneeOpt.ifPresent(newAssignee -> {
                existingTestCase.setAssignedTo(newAssignee);

                boolean isDifferentUser = previousAssignee == null || !previousAssignee.getEmail().equalsIgnoreCase(newAssignee.getEmail());
                boolean notLoggedInUser = !newAssignee.getEmail().equalsIgnoreCase(loggedInUserEmail);

                if (isDifferentUser && notLoggedInUser) {
                    emailService.sendAssignmentNotification(newAssignee, existingTestCase, request);
                }
            });
        }

        if (testCaseDto.getModuleId() != null) {
            existingTestCase.setModule(moduleService.get(testCaseDto.getModuleId()));
        }

        if (testCaseDto.getProjectId() != null) {
            Project project = projectService.getProjectById(testCaseDto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            existingTestCase.setProject(project);
        }

        if (testCaseDto.getTagIds() != null) {
            List<Tag> selectedTags = tagService.getAll();
            existingTestCase.setTags(selectedTags);
        }

        testCaseService.save(existingTestCase, attachmentUuids);


        return "redirect:/tests/" + testCaseDto.getTestCaseId();
    }



    @GetMapping("/all")
    public String getAllTestCases(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long moduleId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignedTo,
            Model model) {

        List<TestCase> testCases = testCaseService.getFilteredTestCases(projectId, moduleId, status, assignedTo);

        List<TestCaseDto> testCaseDtos = testCases.stream().map(testCase ->
                TestCaseDto.builder()
                        .title(testCase.getTitle())
                        .testCaseId(testCase.getTestCaseId())
                        .description(testCase.getDescription())
                        .priority(String.valueOf(testCase.getPriority()))
                        .status(String.valueOf(testCase.getStatus()))
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
        model.addAttribute("title", "All Test Cases");
        model.addAttribute("testCases", testCaseDtos);
        model.addAttribute("projectId", projectId);
        model.addAttribute("moduleId", moduleId);
        model.addAttribute("status", status);
        model.addAttribute("assignedTo", assignedTo);

        // Add necessary lists for dropdowns
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("modules", moduleService.getAll());
        model.addAttribute("users", userService.findAllUsers());

        return "tests/lists";
    }



    @GetMapping("/edit/{id}")
    public String editTestCase(@PathVariable Long id, Model model) {
        TestCaseDto testCaseDto = testCaseService.getTestCaseById(id);
        model.addAttribute("title", "Edit Test Case");
        model.addAttribute("testCaseDto", testCaseDto);
        model.addAttribute("projects", projectService.getAllProjects());

        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("tags", tagService.getAll());

        return "tests/edit";
    }


    @GetMapping("/{id}")
    public String showTestCase(@PathVariable Long id, Model model) {
        TestCaseDto testCaseDto = testCaseService.getTestCaseById(id);
        model.addAttribute("title", testCaseDto.getTitle());
        model.addAttribute("testCaseDto", testCaseDto);
        model.addAttribute("projects", projectService.getAllProjects());

        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("tags", tagService.getAll());
        return "tests/show";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String deleteTestCase(@PathVariable Long id) {
        testCaseService.delete(id);
        return "redirect:/tests/all";
    }
}
