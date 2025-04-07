package com.fantasticsix.testvault;

import com.fantasticsix.testvault.controller.AuthController;
import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Test
    void shouldReturnLoginPageView() {
        String viewName = authController.login();
        assertEquals("login", viewName);
    }

    @Test
    void shouldShowRegistrationFormWithEmptyUserDto() {
        String viewName = authController.showRegistrationForm(model);
        verify(model).addAttribute(eq("user"), any(UserDto.class));
        assertEquals("register", viewName);
    }

    @Test
    void shouldRegisterUserSuccessfullyWhenNoValidationErrors() {
        UserDto userDto = new UserDto();
        userDto.setEmail("user@example.com");

        when(userService.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = authController.registration(userDto, bindingResult, model);

        verify(userService).saveUser(userDto);
        assertEquals("redirect:/register?success", view);
    }

    @Test
    void shouldRejectDuplicateEmailDuringRegistration() {
        UserDto userDto = new UserDto();
        userDto.setEmail("duplicate@example.com");

        User existingUser = new User();
        existingUser.setEmail("duplicate@example.com");

        BindingResult result = mock(BindingResult.class);
        Model model = mock(Model.class);

        when(userService.findByEmail("duplicate@example.com")).thenReturn(Optional.of(existingUser));
        when(result.hasErrors()).thenReturn(true);

        String viewName = authController.registration(userDto, result, model);

        assertEquals("register", viewName);
        verify(model).addAttribute("user", userDto);
        verify(result).rejectValue("email", null, "There is already an account existed with this email");
    }

    @Test
    void shouldReturnToRegisterPageIfValidationErrorsExist() {
        UserDto userDto = new UserDto();
        userDto.setEmail("new@example.com");

        when(userService.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = authController.registration(userDto, bindingResult, model);

        verify(model).addAttribute("user", userDto);
        verify(userService, never()).saveUser(any());
        assertEquals("register", view);
    }
}