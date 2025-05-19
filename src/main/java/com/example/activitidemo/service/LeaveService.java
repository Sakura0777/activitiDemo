package com.example.activitidemo.service;

import com.example.activitidemo.bean.Leave;


import java.util.List;
import java.util.Map;

public interface LeaveService {
    String newLeaveProcessByUserName(String userName);

    boolean applyLeaveByProcessId(Map<String, String> leaveInfo);

    List<Leave> getLeaveApproveByUserName(Map<String, String> searchInfo);

    /*通过用户名查询历史任务 */
    List<Leave> getLeaveHistoryByUserName(Map<String, String> searchInfo);

    /*审批待办任务 输入审批用户名、审批用户角色、任务id、审批意见、审批状态（0-拒绝 1-通过 2-驳回）*/
    Boolean ApproveLeaveTask(Map<String, String> approveInfo);
}
