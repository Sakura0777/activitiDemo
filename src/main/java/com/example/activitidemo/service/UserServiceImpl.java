package com.example.activitidemo.service;

import com.example.activitidemo.bean.User;
import com.example.activitidemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;

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
    public User getUsersByUserName(String userName)throws RuntimeException {

        Map<String,String> map =  userMapper.getUsersByUserName(userName);
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return  user;
    }

    @Override
    public String getSaltByUserName(String userName) {
        String salt =  userMapper.getSaltByUserName(userName);
        if(salt == null){
           return  "noResult";
        }
        return  salt;
    }

    @Override
    public Boolean newUser(User user) {
        String userId = String.valueOf(UUID.randomUUID());
        System.out.println("uuid"+userId);
        Integer rows =  userMapper.newUser(userId,user.getUserName(),user.getUserRole(),user.getPassword(),user.getDepartment(),user.getSalt());
        return rows != 0;
    }

    @Override
    public Map<String, String> Login(Map<String, String> loginInfo) {
        String name = loginInfo.get("userName");
        String password = loginInfo.get("password");
        System.out.println("name"+name);
        User user = getUsersByUserName(name);
        System.out.println("password"+password);
        System.out.println("user"+user.getPassword());
        Map<String,String> res = new HashMap<>();

        if(Objects.equals(user.getPassword(), password)){
            res.put("status","1");
            res.put("userName",name);
            res.put("userRole",user.getUserRole());
            res.put("department",user.getDepartment());
        }else{
            res.put("status","0");
        }
        return  res;
    }
}
