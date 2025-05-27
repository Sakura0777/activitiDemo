package com.example.activitidemo.service;

import com.example.activitidemo.bean.UserInfo;
import com.example.activitidemo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public List<UserInfo> getAllUsers(){
        return userInfoMapper.getAllUsers();
    }

    @Override
    public List<UserInfo> getUsersByRoleAndDept(String userRole,String department) {
        return userInfoMapper.getUsersByRoleAndDept(userRole,department);
    }

    @Override
    public List<UserInfo> getUsersByUserName(String userName) {
        return userInfoMapper.getUsersByUserName(userName);
    }
}
