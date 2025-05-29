package com.example.activitidemo.controller;

import com.example.activitidemo.bean.User;
import com.example.activitidemo.service.UserService;
import com.example.activitidemo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
   private  UserService userService;

    @PostMapping(value = "/getAllUser")
    public List<User> getAllUser(){
        return  userService.getAllUsers();
    }

    @PostMapping(value = "/checkUserName")
    public Boolean checkUserName(@RequestBody Map<String,String> request){
        User users = userService.getUsersByUserName(request.get("userName"));
        System.out.println("checkUserName"+users.getUserName());
        return users.getUserName()  == null;
    }

    @PostMapping(value = "/getSalt")
    public String getSalt(@RequestBody Map<String,String> request){
        String  salt =  userService.getSaltByUserName(request.get("userName"));
        System.out.println("salt"+salt);
        return  salt;
    }

    @PostMapping(value = "/newUser")
    public Boolean newUser(@Valid @RequestBody User user){
        Boolean isOK = userService.newUser(user);
        System.out.println("newUser"+isOK);
        return isOK;

    }
    @PostMapping(value = "/login")
    public Map<String, String> login( @RequestBody Map<String,String> loginInfo){
        return userService.Login(loginInfo);
    }
}
