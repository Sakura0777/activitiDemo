package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Travel implements Serializable {
    private String processId;
    private String userName;
    private String userId;
    private String dateTime;//发起时间
    private String startDate;
    private String endDate;
    private String reason;
    private String approveStatus = "0";//1 技术经理审批通过 2技术经理审批拒绝 3 部门经理审批通过 4部门经理审批驳回 5总经理审批通过 总经理审批拒绝
    private String approveRole;
    private String managerUser;
    private  String managerApproveOpinion;
    private String directorUser;
    private String directorApproveOpinion;
    private String bossUser;
    private String bossApproveOpinion;
}
