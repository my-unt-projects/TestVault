package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.enums.ProjectStatus;
import com.fantasticsix.testvault.model.Project;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestCaseController {
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("testCase", new TestCase());
        return "tests/create";
    }
}
