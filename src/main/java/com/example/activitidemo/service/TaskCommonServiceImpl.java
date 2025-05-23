package com.example.activitidemo.service;

import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.TaskSearchRequest;
import com.example.activitidemo.bean.TodoApprove;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("TaskCommonService")
public class TaskCommonServiceImpl implements TaskCommonService {

    @Override
    public List<TodoApprove> getAllTodoTaskByUserName(TaskSearchRequest searchInfo){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(searchInfo.getUserName()).list();
        System.out.println("taskList"+taskList);
        List<TodoApprove> todoList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Task task:taskList){
            Object item = taskService.getVariable(task.getId(),"details");
            System.out.println("---------------"+task.getId());
            System.out.println("++++++++++++"+taskService.getVariable(task.getId(),"taskName"));
            System.out.println("++++++++++++"+task.getCreateTime());
            TodoApprove todo = new TodoApprove();
            todo.setCreateUser((String) taskService.getVariable(task.getId(),"createUser"));
            todo.setTaskName((String) taskService.getVariable(task.getId(),"taskName"));
            todo.setTaskType((String) taskService.getVariable(task.getId(),"taskType"));

            LocalDateTime createdTime = task.getCreateTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            String formattedCreatedTime = createdTime.format(formatter);

            System.out.println(createdTime);
            System.out.println(formattedCreatedTime);
            todo.setCreatedTime(formattedCreatedTime);

            todo.setDetails(taskService.getVariable(task.getId(),"details"));
            todoList.add((todo) );
        }
        return  todoList;
    }
}
