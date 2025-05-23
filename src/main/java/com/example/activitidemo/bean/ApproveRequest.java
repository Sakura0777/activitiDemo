package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ApproveRequest {
    @NotBlank(message = "流程id不能为空")
    private  String processId;
    @NotBlank(message = "审批用户角色不能为空")
    private String approveRole;
    @NotBlank(message = "审批用户名不能为空")
    private String approveUser;
    @NotBlank(message = "审批结果不能为空")
    private  String approveStatus;
    @Size(max = 50, message = "审批意见不能超过50个字符")
    private String approveOpinion;
}
