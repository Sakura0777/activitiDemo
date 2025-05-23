package com.example.activitidemo.controller;

import com.example.activitidemo.bean.TaskSearchRequest;
import com.example.activitidemo.bean.TodoApprove;
import com.example.activitidemo.service.TaskCommonService;
import com.example.activitidemo.service.TaskCommonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("task")
public class TaskController {
    @Resource
    private TaskCommonService taskCommonService = new TaskCommonServiceImpl();

    @RequestMapping(value = "/getAllTodoTaskByUserName",method = RequestMethod.POST)
    public ResponseEntity<List<TodoApprove>> getAllTodoTask(@Valid @RequestBody TaskSearchRequest searchInfo){
        return ResponseEntity.ok(taskCommonService.getAllTodoTaskByUserName(searchInfo));
//        return taskCommonService.getAllTodoTaskByUserName(searchInfo);
    }

}

