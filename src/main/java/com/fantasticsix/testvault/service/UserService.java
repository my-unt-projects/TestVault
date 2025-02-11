package com.fantasticsix.testvault.service;


import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    Optional<User> findByEmail(String email);

    List<UserDto> findAllUsers();
}
