package com.example.activitidemo.controller;


import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.LeaveTodo;
import com.example.activitidemo.service.LeaveService;
import com.example.activitidemo.service.LeaveServiceImpl;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Resource
    private LeaveService leaveService = new LeaveServiceImpl();

    @RequestMapping(value = "/getLeaveApproveByUserName",method = RequestMethod.POST)
    public List<Leave> getLeaveByUserId(@RequestBody Map<String, Object> searchInfo){
        return leaveService.getLeaveApproveByUserName(searchInfo);
    }
    @PostMapping(value = "/newLeaveApplication")
    public Boolean newLeaveProcessByUserName(@RequestBody Map<String, Object> leaveInfo)  {
        System.out.println("newLeaveProcessByUserName----"+leaveInfo);
        System.out.println("newLeaveProcessByUserName----"+leaveInfo.get("userName"));
        return leaveService.newLeaveProcessByUserName(leaveInfo);
    }
    @PostMapping("/leaveTaskApprove")
    public Boolean ApproveLeaveTask(@RequestBody Map<String, Object> approveInfo)  {
        System.out.println("ApproveLeaveTask----"+approveInfo);
        return leaveService.ApproveLeaveTask(approveInfo);
    }
}
