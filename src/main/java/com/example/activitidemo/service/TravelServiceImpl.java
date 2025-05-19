package com.example.activitidemo.service;

import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.Travel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
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

@Service("travelService")
public class TravelServiceImpl implements TravelService{
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
        map.put("assignee1","佐伊");
        map.put("assignee2","盖伦");
        map.put("assignee3","黑默丁格");
        // 5. 设置启动人
        Authentication.setAuthenticatedUserId(userName);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,map);
        return  instance.getId();
    }

    @Override
    public boolean applyTravelByProcessId(Map<String, String> travelInfo){
        String processId = travelInfo.get("processId");
        if(processId == null || processId.isEmpty()){
            processId = newTravelProcessByUserName(travelInfo.get("userName"));
        }

        Travel travel = new Travel();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shangehai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        travel.setDateTime(formattedDateTime);
        travel.setUserName(travelInfo.get("userName"));
        travel.setStartDate(travelInfo.get("startDate"));
        travel.setEndDate(travelInfo.get("endDate"));
        travel.setUserId(travelInfo.get("userId"));
        travel.setReason(travelInfo.get("reason"));

        Task task = taskService.createTaskQuery().processInstanceId(processId)
                .taskAssignee(travelInfo.get("userName")).singleResult();
        Map<String,Object> travelMap = new HashMap<>();
        travel.setProcessId(processId);
        travelMap.put("details",travel);
        taskService.complete(task.getId(),travelMap);
        return  true;
    }

    /*
        审批待办任务
        输入审批用户名、 userName 审批用户角色 approveRole、任务id processId、审批意见approveOpinion、
        审批状态 approveStatus（1 技术经理审批通过 2技术经理审批拒绝 3 部门经理审批通过 4部门经理审批驳回 5总经理审批通过 总经理审批拒绝）*/
    @Override
    public Boolean ApproveTravelTask(Map<String, String> approveInfo){
        Task task =taskService.createTaskQuery().processDefinitionKey(key).processInstanceId(approveInfo.get("processId")).singleResult();
        Travel details = (Travel) taskService.getVariable(task.getId(),"details");
        Map<String,Object> map = new HashMap<>();
        map.put("approveStatus",Integer.parseInt(approveInfo.get("approveStatus")));
        details.setApproveStatus( approveInfo.get("approveStatus"));
        if(approveInfo.get("approveRole").equals("1")){
            details.setDirectorApproveOpinion(approveInfo.get("approveOpinion"));
        }else if(approveInfo.get("approveRole").equals("2")){
            details.setManagerApproveOpinion(approveInfo.get("approveOpinion"));
        }else {
            details.setBossApproveOpinion(approveInfo.get("approveOpinion"));
        }
        map.put("details",details);
        taskService.complete(task.getId(),map);
        return  true;
    }
    /*通过用户名查询待办出差审批*/
    @Override
    public List<Travel> getTravelApproveByUserName(Map<String,String> searchInfo){
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(searchInfo.get("userName")).list();
        List<Travel> travelList = new ArrayList<>();
        for(Task task:taskList){
            travelList.add((Travel) taskService.getVariable(task.getId(),"details"));
        }
        return  travelList;
    }
    /*通过用户名查询自己发起的审批任务 */
    @Override
    public List<Leave> getTravelHistoryByUserName(Map<String, String> searchInfo){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> processesStartedByUser = historyService
                .createHistoricProcessInstanceQuery()
                .startedBy(searchInfo.get("userName")) // 如 "admin"
                .processDefinitionKey(key)
//                .finished() // 可选：仅已完成的流程
                .list();
        System.out.println("共有多少个任务"+processesStartedByUser.size());
        List<Leave> historyList = new ArrayList<>();
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
                Leave value = (Leave) variable.getValue();
                historyList.add(value);
            }
        }
        return historyList;
    }





}
