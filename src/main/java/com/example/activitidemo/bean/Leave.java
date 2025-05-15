package com.example.activitidemo.bean;


import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;


@Setter
@Getter
public class Leave implements Serializable {
    private String processId;
    private String userName;
    private Integer userId;
    private  String dateTime;
    private String startDate;
    private  Integer days;
    private  String reason;
    private String approveStatus = "0"; //1 部门经理审批通过 2部门经理审批拒绝 3 部门经理审批驳回 4部门总监审批通过 5部门总监审批拒绝 6部门总监审批驳回
    private String approveRole;
    private String managerUser;
    private  String managerApproveOpinion;
    private String directorUser;
    private String directorApproveOpinion;
}
