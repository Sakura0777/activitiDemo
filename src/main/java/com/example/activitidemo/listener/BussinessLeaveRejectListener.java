package com.example.activitidemo.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class BussinessLeaveRejectListener implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
//        可以在此处发送审批拒绝的通知
        System.out.println("*******请假审批拒绝*******"+delegateExecution.getParent());
        System.out.println("*******请假审批拒绝*******"+delegateExecution.getVariable("managerApproveStatus"));
        System.out.println("*******请假审批拒绝*******"+delegateExecution.getVariable("directorApproveStatus"));

    }
}
