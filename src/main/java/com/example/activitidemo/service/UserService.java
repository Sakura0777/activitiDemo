package com.example.activitidemo.service;

import com.example.activitidemo.bean.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<User> getUsersByRoleAndDept(String userRole, String department);

    List<User> getUsersByUserName(String userName);
    Boolean newUser(User user);

}
