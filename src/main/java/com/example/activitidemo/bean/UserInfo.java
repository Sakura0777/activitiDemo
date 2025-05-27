package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String userId;
    private String userName;
    private String userRole; //0 -普通员工 1部门经理 2部门总监 3人事 4总经理
    private String department; //development 开发部 sale 销售部 admin 行政部

}
