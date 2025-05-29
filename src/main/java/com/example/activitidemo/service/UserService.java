package com.example.activitidemo.service;

import com.example.activitidemo.bean.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();
    List<User> getUsersByRoleAndDept(String userRole, String department);

    User getUsersByUserName(String userName);

    String getSaltByUserName(String userName);
    Boolean newUser(User user);
    Map<String, String> Login(Map<String,String> loginInfo);

}
