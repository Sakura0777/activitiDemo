package com.example.activitidemo.service;

import com.example.activitidemo.bean.Leave;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("leaveService")
public class LeaveServiceImpl implements LeaveService {
    String key = "leaveApplication";
    /*通过用户名新建请假工单*/
    @Override
    public boolean newLeaveProcessByUserName(Map<String, Object> leaveInfo){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /*RuntimeService：提供了处理流程实例不同步骤的结构和行为。包括启动流程实例、暂停和激活流程实例等功能*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0",leaveInfo.get("userName"));
        map.put("assignee1","佐伊");
        map.put("assignee2","盖伦");
        /*
         map 则是为其启动之后的流程实例赋值*/
        //启动请假申请流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,map);

        Leave leave = new  Leave();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        leave.setDateTime(formattedDateTime);
        leave.setUserName((String) leaveInfo.get("userName"));
        leave.setDays(Integer.parseInt((String) leaveInfo.get("days")));
        leave.setReason((String) leaveInfo.get("reason"));
        leave.setStartDate((String) leaveInfo.get("startDate"));
        leave.setUserId((Integer) leaveInfo.get("userId"));
//        发起申请
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId())
                .taskAssignee((String) leaveInfo.get("userName")).singleResult();

        Map<String,Object>leaveMap = new HashMap<String,Object>();
        leave.setProcessId(task.getProcessInstanceId());
        leaveMap.put("leaveDetails",leave);
        taskService.complete(task.getId(),leaveMap);
        return true;
    }
    /*通过用户名查询待办请假审批*/
    @Override
    public List<Leave> getLeaveApproveByUserName(Map<String, Object> searchInfo){
        System.out.println("---------------"+searchInfo);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /*TaskService：提供有关任务相关功能的服务。包括任务的查询、删除以及完成等功能*/
        TaskService taskService = processEngine.getTaskService();
        /*通过 TaskService 进行查询，分别通过请假流程的 key 和用户名称，查询该用户名下的所有任务*/
        List<Task>  taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee((String) searchInfo.get("userName")).list();
        List<Leave> leaveList = new ArrayList<>();
        for (Task task:taskList){
            System.out.println("---------------"+task.getId());
            System.out.println("---------------"+taskService.getVariable(task.getId(),"leaveDetails"));
            leaveList.add((Leave) taskService.getVariable(task.getId(),"leaveDetails"));
        }
        return  leaveList;
    }
/*
    审批待办任务
    输入审批用户名、 userName 审批用户角色 approveRole、任务id processId、审批意见approveOpinion、
    审批状态 approveStatus（0-拒绝 1-通过 2-驳回）*/
    @Override
    public Boolean ApproveLeaveTask(Map<String, Object> approveInfo){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processDefinitionKey(key).processInstanceId((String) approveInfo.get("processId")).singleResult();
        Leave leaveDetails = (Leave) taskService.getVariable(task.getId(),"leaveDetails");
        System.out.println("leaveDetails"+leaveDetails);
        Map<String,Object>map = new HashMap<String,Object>();
//        approveRole:1-项目经理 2项目总监 3人事
        if(approveInfo.get("approveRole").equals("1") ){
            map.put("managerApproveStatus",Integer.parseInt((String) approveInfo.get("approveStatus")));
            leaveDetails.setManagerApproveStatus((String) approveInfo.get("approveStatus"));
            leaveDetails.setManagerApproveOpinion((String) approveInfo.get("approveOpinion"));
        } else if (approveInfo.get("approveRole").equals("2")){
            map.put("directorApproveStatus",Integer.parseInt((String) approveInfo.get("approveStatus")));
            leaveDetails.setDirectorApproveStatus((String) approveInfo.get("approveStatus"));
            leaveDetails.setDirectorApproveOpinion((String) approveInfo.get("approveOpinion"));
        }
        map.put("leaveDetails",leaveDetails);
        /*完成任务*/
        taskService.complete(task.getId(),map);
        return  true;
    }

}
