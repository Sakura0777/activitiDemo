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
    private Integer days;
    private String reason;
    private String city;
    private String approveStatus = "0";//1 部门经理审批通过 2部门经理审批拒绝 3 技术总监审批通过 4技术总监拒绝 5总经理审批通过 总经理审批拒绝
    private String approveRole;
    private String managerUser;
    private String managerApproveOpinion;
    private String directorUser;
    private String directorApproveOpinion;
    private String bossUser;
    private String bossApproveOpinion;
}
