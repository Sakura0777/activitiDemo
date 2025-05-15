package com.example.activitidemo.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class ManagerCompleteListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        System.out.println("-------监听到事件------"+delegateExecution.getEventName());
        System.out.println("-------监听到事件------"+delegateExecution.getCurrentActivityId());
        System.out.println("-------审批意见------"+delegateExecution.getVariable("approveStatus"));
    }
}
