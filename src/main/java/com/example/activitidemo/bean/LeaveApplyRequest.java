package com.example.activitidemo.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LeaveApplyRequest {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private String userId;
    private  String processId;
    @NotBlank(message = "请假天数不能为空")
    private String days;

    @NotBlank(message = "请假原因不能为空")
    @Size(max = 50, message = "请假原因不能超过50个字符")
    private String reason;

    @NotBlank(message = "起始日期不能为空")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "日期格式应为yyyy-MM-dd")
    private String startDate;

}
