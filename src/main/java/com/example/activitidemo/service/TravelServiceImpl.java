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

@Service("travelService")
public class TravelServiceImpl implements TravelService{
    @Autowired
            private UserInfoService userInfoService;
    String key = "businessTravelProcessParallel";
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /*TaskService：提供有关任务相关功能的服务。包括任务的查询、删除以及完成等功能*/
    TaskService taskService = processEngine.getTaskService();
    /*通过用户名新建出差工单*/
    @Override
    public String newTravelProcessByUserName(String userName){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0",userName);
        UserInfo userInfo = userInfoService.getUsersByUserName(userName).get(0);
        String department = userInfo.getDepartment();
        UserInfo userInfo1 = userInfoService.getUsersByRoleAndDept("1",department).get(0);
        UserInfo userInfo2 = userInfoService.getUsersByRoleAndDept("2",department).get(0);
        UserInfo userInfo3 = userInfoService.getUsersByRoleAndDept("3","admin").get(0);
        System.out.println("++++++++++++++++++++++"+department);
        System.out.println("++++++++++++++++++++++"+userInfo1.getUserName());
        System.out.println("++++++++++++++++++++++"+userInfo2.getUserName());

        map.put("assignee1",userInfo1.getUserName());
        map.put("assignee2",userInfo2.getUserName());
        map.put("assignee3",userInfo3.getUserName());
        // 5. 设置启动人
        Authentication.setAuthenticatedUserId(userName);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,map);
        return  instance.getId();
    }

    @Override
    public boolean applyTravelByProcessId(Travel travelInfo){
        String processId = travelInfo.getProcessId();
        if(processId == null || processId.isEmpty()){
            processId = newTravelProcessByUserName(travelInfo.getUserName());
        }

        Travel travel = new Travel();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        travel.setDateTime(formattedDateTime);
        travel.setUserName(travelInfo.getUserName());
        travel.setStartDate(travelInfo.getStartDate());
        travel.setEndDate(travelInfo.getEndDate());
        travel.setUserId(travelInfo.getUserId());
        travel.setReason(travelInfo.getReason());
        travel.setDays(travelInfo.getDays());
        travel.setCity(travelInfo.getCity());

        Task task = taskService.createTaskQuery().processInstanceId(processId)
                .taskAssignee(travelInfo.getUserName()).singleResult();
        Map<String,Object> travelMap = new HashMap<>();
        travel.setProcessId(processId);
        travelMap.put("createUser",travelInfo.getUserName());
        travelMap.put("taskName",travelInfo.getUserName()+"的出差申请");
        travelMap.put("taskType","travel");
        travelMap.put("details",travel);
        taskService.complete(task.getId(),travelMap);
        return  true;
    }

    /*
        审批待办任务
        输入审批用户名、 userName 审批用户角色 approveRole、任务id processId、审批意见approveOpinion、
        审批状态 approveStatus（1 技术经理审批通过 2技术经理审批拒绝 3 部门经理审批通过 4部门经理审批驳回 5总经理审批通过 总经理审批拒绝）*/
    @Override
    public Boolean ApproveTravelTask(ApproveRequest approveInfo){
        Task task =taskService.createTaskQuery().processDefinitionKey(key)
                .processInstanceId(approveInfo.getProcessId()).taskAssignee(approveInfo.getApproveUser()).singleResult();
        Travel details = (Travel) taskService.getVariable(task.getId(),"details");
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",Integer.parseInt(approveInfo.getApproveStatus()));
        details.setApproveStatus(approveInfo.getApproveStatus());
        if(approveInfo.getApproveRole().equals("1")){
            details.setManagerApproveOpinion(approveInfo.getApproveOpinion());
            details.setManagerUser(approveInfo.getApproveUser());
        }else if(approveInfo.getApproveRole().equals("2")){
            details.setDirectorApproveOpinion(approveInfo.getApproveOpinion());
            details.setDirectorUser(approveInfo.getApproveUser());
        }else {
            details.setBossApproveOpinion(approveInfo.getApproveOpinion());
            details.setBossUser(approveInfo.getApproveUser());
        }
        map.put("details",details);
        taskService.complete(task.getId(),map);
        return  true;
    }
    /*通过用户名查询待办出差审批*/
    @Override
    public List<Travel> getTravelApproveByUserName(TaskSearchRequest searchInfo){
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(searchInfo.getUserName()).list();
        List<Travel> travelList = new ArrayList<>();
        for(Task task:taskList){
            travelList.add((Travel) taskService.getVariable(task.getId(),"details"));
        }
        return  travelList;
    }
    /*通过用户名查询自己发起的审批任务 */
    @Override
    public List<Travel> getTravelHistoryByUserName(TaskSearchRequest searchInfo){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> processesStartedByUser = historyService
                .createHistoricProcessInstanceQuery()
                .startedBy(searchInfo.getUserName()) // 如 "admin"
                .processDefinitionKey(key)
//                .finished() // 可选：仅已完成的流程
                .orderByProcessInstanceStartTime().desc().list();
        System.out.println("共有多少个任务"+processesStartedByUser.size());
        List<Travel> historyList = new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance:processesStartedByUser){
            System.out.println("%%%%%%%%%%%%%%%%当前用户发起的审批任务%%%%%% %%%%%%%%");
            System.out.println(historicProcessInstance.getId());
            // 获取特定变量的值
            HistoricVariableInstance variable = historyService
                    .createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .variableName("details")
                    .singleResult();

            if (variable != null) {
                Travel value = (Travel) variable.getValue();
                historyList.add(value);
            }
        }
        return historyList;
    }





}
