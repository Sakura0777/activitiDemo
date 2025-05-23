package com.example.activitidemo.controller;


import com.example.activitidemo.bean.ApproveRequest;
import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.LeaveApplyRequest;
import com.example.activitidemo.bean.TaskSearchRequest;
import com.example.activitidemo.service.LeaveService;
import com.example.activitidemo.service.LeaveServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Resource
    private LeaveService leaveService = new LeaveServiceImpl();


    @RequestMapping("/test-header")
    public ResponseEntity<String> testHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Test-Header", "Test Value");
        return new ResponseEntity<>("Hello", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/getLeaveApproveByUserName",method = RequestMethod.POST)
    public List<Leave> getLeaveApprove(@Valid @RequestBody TaskSearchRequest searchInfo){
        return leaveService.getLeaveApproveByUserName(searchInfo);
    }
    @RequestMapping(value = "/getLeaveHistoryByUserName",method = RequestMethod.POST)
    public List<Leave> getLeaveHistory(@Valid  @RequestBody TaskSearchRequest searchInfo){
        return leaveService.getLeaveHistoryByUserName(searchInfo);
    }
    @PostMapping(value = "/newLeaveApprove")
    public Boolean newLeaveApprove(@Valid @RequestBody LeaveApplyRequest leaveInfo)  {
        System.out.println("newLeaveApprove----"+leaveInfo);
        System.out.println("newLeaveApprove----"+leaveInfo.getUserName());
        return leaveService.applyLeaveByProcessId(leaveInfo);
    }
    @PostMapping("/leaveTaskApprove")
    public Boolean ApproveLeaveTask(@Valid @RequestBody ApproveRequest approveInfo)  {
        System.out.println("ApproveLeaveTask----"+approveInfo);
        return leaveService.ApproveLeaveTask(approveInfo);
    }
}
