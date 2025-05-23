package com.example.activitidemo.service;

import com.example.activitidemo.bean.ApproveRequest;
import com.example.activitidemo.bean.Leave;
import com.example.activitidemo.bean.TaskSearchRequest;
import com.example.activitidemo.bean.Travel;

import java.util.List;
import java.util.Map;

public interface TravelService {
    /*通过用户名新建出差工单*/
    String newTravelProcessByUserName(String username);

    boolean applyTravelByProcessId(Travel travelInfo);

    /*
            审批待办任务
            输入审批用户名、 userName 审批用户角色 approveRole、任务id processId、审批意见approveOpinion、
            审批状态 approveStatus（1 技术经理审批通过 2技术经理审批拒绝 3 部门经理审批通过 4部门经理审批驳回 5总经理审批通过 总经理审批拒绝）*/
    Boolean ApproveTravelTask(ApproveRequest approveInfo);

    /*通过用户名查询待办出差审批*/
    List<Travel> getTravelApproveByUserName(TaskSearchRequest searchInfo);

    /*通过用户名查询自己发起的审批任务 */
    List<Travel> getTravelHistoryByUserName(TaskSearchRequest searchInfo);
}
