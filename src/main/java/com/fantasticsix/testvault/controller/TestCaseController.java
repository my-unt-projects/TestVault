package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.model.Module;
import com.fantasticsix.testvault.service.ModuleService;
import com.fantasticsix.testvault.service.ProjectService;
import com.fantasticsix.testvault.service.TestCaseService;
import com.fantasticsix.testvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestCaseController {
    private final TestCaseService testCaseService;
    private final ProjectService projectService;
    private final UserService userService;
    private final ModuleService moduleService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        return "tests/create";
    }


    @GetMapping("/module/{moduleId}")
    public String listByModule(@PathVariable Long moduleId, Model model) {
        List<TestCase> testCases = testCaseService.getByModuleId(moduleId);
        Module module = moduleService.get(moduleId);
        model.addAttribute("testCases", testCases);
        model.addAttribute("module", module);
        return "tests/list";
    }

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
