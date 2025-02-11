package com.fantasticsix.testvault.advice;

import com.fantasticsix.testvault.dto.UserDetailModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserDetailsToModel(Model model) {
        // Get the current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetailModel userDetail) {

            // Add user details to the model for all pages
            model.addAttribute("email", userDetail.getUsername());
            model.addAttribute("name", userDetail.getName());
        }
    }
}