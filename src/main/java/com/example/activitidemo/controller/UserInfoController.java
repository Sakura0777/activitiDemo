package com.example.activitidemo.controller;

import com.example.activitidemo.bean.UserInfo;
import com.example.activitidemo.service.UserInfoService;
import com.example.activitidemo.service.UserInfoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService = new UserInfoServiceImpl();

    @PostMapping(value = "/getAllUserInfo")
    public List<UserInfo> getAllUserInfo(){
        return  userInfoService.getAllUsers();
    }
}
