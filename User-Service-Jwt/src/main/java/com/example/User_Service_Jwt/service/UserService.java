package com.example.User_Service_Jwt.service;


import com.example.User_Service_Jwt.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
}
