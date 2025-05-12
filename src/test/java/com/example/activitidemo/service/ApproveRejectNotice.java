package com.example.activitidemo.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.task.Task;

public class ApproveRejectNotice implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
//        可以在此处发送审批拒绝的通知
        System.out.println("*******审批拒绝*******"+delegateExecution.getParent());
        System.out.println("*******审批拒绝*******"+delegateExecution.getVariable("managerApproveStatus"));
        System.out.println("*******审批拒绝*******"+delegateExecution.getVariable("directorApproveStatus"));
        
    }
}
