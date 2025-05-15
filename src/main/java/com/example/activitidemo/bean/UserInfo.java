package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Integer userId;
    private String userName;
    private String userRole; //0 -普通员工 1部门经理 2部门总监 3总经理
}
