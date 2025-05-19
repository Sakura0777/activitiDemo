package com.example.activitidemo.controller;

import com.example.activitidemo.bean.TodoApprove;
import com.example.activitidemo.service.TaskCommonService;
import com.example.activitidemo.service.TaskCommonServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("task")
public class taskController {
    @Resource
    private TaskCommonService taskCommonService = new TaskCommonServiceImpl();

    @RequestMapping(value = "/getAllTodoTaskByUserName",method = RequestMethod.POST)
    public List<TodoApprove> getAllTodoTask(@RequestBody Map<String,String> searchInfo){
        return taskCommonService.getAllTodoTaskByUserName(searchInfo);
    }

}
