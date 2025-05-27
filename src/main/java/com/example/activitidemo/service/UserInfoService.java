package com.example.activitidemo.service;

import com.example.activitidemo.bean.UserInfo;

import java.util.List;

public interface UserInfoService {
    List<UserInfo> getAllUsers();
    List<UserInfo> getUsersByRoleAndDept(String userRole,String department);

    List<UserInfo> getUsersByUserName(String userName);

}
