package com.example.javalr8.service;

import com.example.javalr8.dto.UserDto;
import com.example.javalr8.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
