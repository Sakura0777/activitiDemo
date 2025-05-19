package com.example.activitidemo.service;

import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.TodoApprove;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("TaskCommonService")
public class TaskCommonServiceImpl implements TaskCommonService {

    @Override
    public List<TodoApprove> getAllTodoTaskByUserName(Map<String, String> searchInfo){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(searchInfo.get("userName")).list();
        System.out.println("taskList"+taskList);
        List<TodoApprove> todoList = new ArrayList<>();
        for (Task task:taskList){
            System.out.println("---------------"+task.getId());
            System.out.println("---------------"+taskService.getVariable(task.getId(),"details"));
            TodoApprove details = new TodoApprove();
            details.setDetails(taskService.getVariable(task.getId(),"details"));
            todoList.add((details) );
        }
        return  todoList;
    }
}
