package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TodoApprove {
    private String createdTime;
    private  String createUser;
    private String taskName;
    private String taskType;
    private Object details;
}
