

package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.service.TestCaseService;
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

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        return "tests/create";
    }
    @PostMapping
    public String createTestCase(@ModelAttribute TestCase testCase) {
        testCaseService.createTestCase(testCase);
        return "redirect:/tests/all";
    }

    @GetMapping("/all")
    public String getAllTestCases(Model model) {
        List<TestCase> testCases = testCaseService.listOfAllTestCases();
        model.addAttribute("testCases", testCases);
        return "tests/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        TestCase testCase = testCaseService.updateTestCase(id, new TestCase());
        model.addAttribute("testCase", testCase);
        return "tests/list";
    }

    @PostMapping("/update/{id}")
    public String updateTestCase(@PathVariable Long id, @ModelAttribute TestCase testCase) {
        testCaseService.updateTestCase(id, testCase);
        return "redirect:/tests/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteTestCase(@PathVariable Long id) {
        testCaseService.deleteTestCaseById(id);
        return "redirect:/tests/all";
    }


    }


