package com.example.activitidemo.service;

import com.example.activitidemo.bean.User;
import com.example.activitidemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("UserService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }

    @Override
    public List<User> getUsersByRoleAndDept(String userRole, String department) {
        return userMapper.getUsersByRoleAndDept(userRole,department);
    }

    @Override
    public List<User> getUsersByUserName(String userName) {
        return userMapper.getUsersByUserName(userName);
    }

    @Override
    public Boolean newUser(User user) {
        String userId = String.valueOf(UUID.randomUUID());
        System.out.println("uuid"+userId);
        Integer rows =  userMapper.newUser(userId,user.getUserName(),user.getUserRole(),user.getPassword(),user.getDepartment(),user.getSalt());
        return rows != 0;
    }
}
