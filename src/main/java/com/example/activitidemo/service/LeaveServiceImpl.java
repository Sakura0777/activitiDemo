package com.example.activitidemo.service;

import com.example.activitidemo.bean.*;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;
    String key = "leaveApplication";
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /*TaskService：提供有关任务相关功能的服务。包括任务的查询、删除以及完成等功能*/
    TaskService taskService = processEngine.getTaskService();
    /*通过用户名新建请假工单*/
    @Override
    public String newLeaveProcessByUserName(String userName){

        /*RuntimeService：提供了处理流程实例不同步骤的结构和行为。包括启动流程实例、暂停和激活流程实例等功能*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0",userName);
        User user = userService.getUsersByUserName(userName);
        String department = user.getDepartment();
        User user1 = userService.getUsersByRoleAndDept("1",department).get(0);
        User user2 = userService.getUsersByRoleAndDept("2",department).get(0);

        System.out.println("请假申请"+department);
        System.out.println("请假申请"+ user1.getUserName());
        System.out.println("请假申请"+ user2.getUserName());

        map.put("assignee1", user1.getUserName());
        map.put("assignee2", user2.getUserName());
        // 5. 设置启动人
        Authentication.setAuthenticatedUserId(userName);
        /*
         map 则是为其启动之后的流程实例赋值*/
        //启动请假申请流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,map);
        return  instance.getId();
    }
    @Override
    public boolean applyLeaveByProcessId(LeaveApplyRequest leaveInfo){
        String processId = leaveInfo.getProcessId();
        if(processId == null || processId.isEmpty()){
            processId =  newLeaveProcessByUserName(leaveInfo.getUserName());
        }
        Leave leave = new  Leave();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        leave.setDateTime(formattedDateTime);
        leave.setUserName(leaveInfo.getUserName());
        leave.setDays(Integer.parseInt(leaveInfo.getDays()));
        leave.setReason(leaveInfo.getReason());
        leave.setStartDate(leaveInfo.getStartDate());
//        发起申请

        Task task = taskService.createTaskQuery().processInstanceId(processId)
                .taskAssignee(leaveInfo.getUserName()).singleResult();

        Map<String,Object>leaveMap = new HashMap<String,Object>();
        leave.setProcessId(processId);
        leaveMap.put("createUser",leaveInfo.getUserName());
        leaveMap.put("taskName",leaveInfo.getUserName()+"的请假申请");
        leaveMap.put("taskType","leave");
        leaveMap.put("details",leave);
        taskService.complete(task.getId(),leaveMap);
        return true;
    }

    /*通过用户名查询待办请假审批*/
    @Override
    public List<Leave> getLeaveApproveByUserName(TaskSearchRequest searchInfo){
        System.out.println("---------------"+searchInfo);

        /*通过 TaskService 进行查询，分别通过请假流程的 key 和用户名称，查询该用户名下的所有任务*/
        List<Task>  taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(searchInfo.getUserName()).list();
        List<Leave> leaveList = new ArrayList<>();
        for (Task task:taskList){
            System.out.println("---------------"+task.getId());
            System.out.println("---------------"+taskService.getVariable(task.getId(),"details"));
            leaveList.add((Leave) taskService.getVariable(task.getId(),"details"));
        }
        return  leaveList;
    }
    /*通过用户名查询自己发起的审批任务 */
    @Override
    public List<Leave> getLeaveHistoryByUserName(TaskSearchRequest searchInfo){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> processesStartedByUser = historyService
                .createHistoricProcessInstanceQuery()
                .startedBy(searchInfo.getUserName()) // 如 "admin"
                .processDefinitionKey(key)
//                .finished() // 可选：仅已完成的流程
                .orderByProcessInstanceStartTime().desc().list();
        System.out.println("共有多少个任务"+processesStartedByUser.size());
        List<Leave> historyList = new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance:processesStartedByUser){
            System.out.println("%%%%%%%%%%%%%%%%当前用户发起的历史任务%%%%%% %%%%%%%%");
            System.out.println(historicProcessInstance.getId());
            // 获取特定变量的值
            HistoricVariableInstance variable = historyService
                    .createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .variableName("details")
                    .singleResult();

            if (variable != null) {
                Leave value = (Leave) variable.getValue();
                historyList.add(value);
            }
        }
        return historyList;
    }

/*
    审批待办任务
    输入审批用户名、 userName 审批用户角色 approveRole、任务id processId、审批意见approveOpinion、
    审批状态 approveStatus（0-拒绝 1-通过 2-驳回）*/
    @Override
    public Boolean ApproveLeaveTask(ApproveRequest approveInfo){
        Task task = taskService.createTaskQuery().processDefinitionKey(key).processInstanceId(approveInfo.getProcessId()).singleResult();
        Leave details = (Leave) taskService.getVariable(task.getId(),"details");
        System.out.println("details"+details);
        Map<String,Object>map = new HashMap<String,Object>();
//        approveRole:1-项目经理 2项目总监 3人事
        if(approveInfo.getApproveRole().equals("1") ){
            map.put("approveStatus",Integer.parseInt(approveInfo.getApproveStatus()));
            details.setApproveStatus( approveInfo.getApproveStatus());
            details.setManagerApproveOpinion(approveInfo.getApproveOpinion());
        } else if (approveInfo.getApproveRole().equals("2")){
            map.put("approveStatus",Integer.parseInt( approveInfo.getApproveStatus()));
            details.setApproveStatus(approveInfo.getApproveStatus());
            details.setDirectorApproveOpinion(approveInfo.getApproveOpinion());
        }
        map.put("details",details);
        /*完成任务*/
        taskService.complete(task.getId(),map);
        return  true;
    }

}
