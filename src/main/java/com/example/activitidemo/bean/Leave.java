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
    private String approveRole;
    private String managerUser;
    private String managerApproveStatus;
    private  String managerApproveOpinion;
    private String directorUser;
    private String directorApproveStatus;
    private String directorApproveOpinion;
}
