package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class User {
    private String userId;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "用户角色不能为空")
    private String userRole; //0 -普通员工 1部门经理 2部门总监 3人事 4总经理
    @NotBlank(message = "所属部门不能为空")
    private String department; //development 开发部 sale 销售部 admin 行政部
    private String password;
    private String salt;//前端密码加密用的盐

}
