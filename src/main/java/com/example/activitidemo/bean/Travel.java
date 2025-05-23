package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class Travel implements Serializable {
    private String processId;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private String userId;
    private String dateTime;//发起时间
    @NotBlank(message = "起始日期不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "日期格式应为yyyy-MM-dd")
    private String startDate;
    @NotBlank(message = "结束日期不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "日期格式应为yyyy-MM-dd")
    private String endDate;
    private Integer days;
    @NotBlank(message = "出差原因不能为空")
    @Size(max = 50, message = "出差原因不能超过50个字符")
    private String reason;
    @NotBlank(message = "出差地点不能为空")
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
