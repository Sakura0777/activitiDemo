package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TaskSearchRequest {
    @NotBlank(message = "用户名不能为空")
    private String userName;
}
