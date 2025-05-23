package com.example.activitidemo.service;

import com.example.activitidemo.bean.ApproveRequest;
import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.LeaveApplyRequest;
import com.example.activitidemo.bean.TaskSearchRequest;


import java.util.List;
import java.util.Map;

public interface LeaveService {
    String newLeaveProcessByUserName(String userName);

    boolean applyLeaveByProcessId(LeaveApplyRequest leaveInfo);

    List<Leave> getLeaveApproveByUserName(TaskSearchRequest searchInfo);

    /*通过用户名查询历史任务 */
    List<Leave> getLeaveHistoryByUserName(TaskSearchRequest searchInfo);

    /*审批待办任务 输入审批用户名、审批用户角色、任务id、审批意见、审批状态（0-拒绝 1-通过 2-驳回）*/
    Boolean ApproveLeaveTask(ApproveRequest approveInfo);
}
