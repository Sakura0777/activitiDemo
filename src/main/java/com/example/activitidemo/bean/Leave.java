package com.example.activitidemo.bean;


import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Setter
@Getter
public class Leave implements Serializable {
    private String processId;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private Integer userId;
    private  String dateTime;
    @NotBlank(message = "起始日期不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "日期格式应为yyyy-MM-dd")
    private String startDate;
    private  Integer days;
    @NotBlank(message = "请假原因不能为空")
    @Size(max = 50, message = "请假原因不能超过50个字符")
    private  String reason;
    private String approveStatus = "0"; //1 部门经理审批通过 2部门经理审批拒绝 3 部门经理审批驳回 4部门总监审批通过 5部门总监审批拒绝 6部门总监审批驳回
    private String approveRole;
    private String managerUser;
    private  String managerApproveOpinion;
    private String directorUser;
    private String directorApproveOpinion;
}
