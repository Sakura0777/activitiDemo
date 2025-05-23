package com.example.activitidemo.service;

import com.example.activitidemo.bean.TaskSearchRequest;
import com.example.activitidemo.bean.TodoApprove;

import java.util.List;
import java.util.Map;

public interface TaskCommonService {
    List<TodoApprove> getAllTodoTaskByUserName(TaskSearchRequest searchInfo);
}
