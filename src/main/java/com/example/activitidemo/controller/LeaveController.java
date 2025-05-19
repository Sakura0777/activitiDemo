package com.example.activitidemo.controller;


import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.service.LeaveService;
import com.example.activitidemo.service.LeaveServiceImpl;
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
    public List<Leave> getLeaveApprove(@RequestBody Map<String, String> searchInfo){
        return leaveService.getLeaveApproveByUserName(searchInfo);
    }
    @RequestMapping(value = "/getLeaveHistoryByUserName",method = RequestMethod.POST)
    public List<Leave> getLeaveHistory(@RequestBody Map<String, String> searchInfo){
        return leaveService.getLeaveHistoryByUserName(searchInfo);
    }
    @PostMapping(value = "/newLeaveApplication")
    public Boolean newLeaveApprove(@RequestBody Map<String,String> leaveInfo)  {
        System.out.println("newLeaveApplication----"+leaveInfo);
        System.out.println("newLeaveApplication----"+leaveInfo.get("userName"));
        return leaveService.applyLeaveByProcessId(leaveInfo);
    }
    @PostMapping("/leaveTaskApprove")
    public Boolean ApproveLeaveTask(@RequestBody Map<String, String> approveInfo)  {
        System.out.println("ApproveLeaveTask----"+approveInfo);
        return leaveService.ApproveLeaveTask(approveInfo);
    }
}
