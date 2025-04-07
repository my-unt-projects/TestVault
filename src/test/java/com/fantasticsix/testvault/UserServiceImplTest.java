package com.fantasticsix.testvault;

import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.Role;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.RoleRepository;
import com.fantasticsix.testvault.repository.UserRepository;
import com.fantasticsix.testvault.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveUserWithExistingRole() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("secret");

        Role role = new Role();
        role.setName("ROLE_USER");

        when(passwordEncoder.encode("secret")).thenReturn("encodedSecret");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);

        userService.saveUser(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals("encodedSecret", savedUser.getPassword());
        assertEquals(List.of(role), savedUser.getRoles());
    }

    @Test
    void shouldSaveUserAndCreateRoleIfNotExists() {
        UserDto userDto = new UserDto();
        userDto.setName("Jane");
        userDto.setEmail("jane@example.com");
        userDto.setPassword("pass");

        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);

        Role roleToSave = new Role();
        roleToSave.setName("ROLE_ADMIN");

        when(roleRepository.save(any(Role.class))).thenReturn(roleToSave);

        userService.saveUser(userDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("Jane", savedUser.getName());
        assertEquals("jane@example.com", savedUser.getEmail());
        assertEquals("encodedPass", savedUser.getPassword());
        assertEquals(List.of(roleToSave), savedUser.getRoles());
    }

    @Test
    void shouldFindUserByEmailIgnoreCase() {
        User user = new User();
        user.setEmail("john@example.com");

        when(userRepository.findByEmailIgnoreCase("john@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("john@example.com");

        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getEmail());
    }

    @Test
    void shouldReturnAllUserDtos() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Alice");
        user1.setEmail("alice@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Bob");
        user2.setEmail("bob@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> users = userService.findAllUsers();

        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getName());
        assertEquals("bob@example.com", users.get(1).getEmail());
    }
}