package com.example.activitidemo.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;


@Service
public class BusinessTravelPassListener implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("-------出差审批通过------");
        System.out.println("-------事件名称------"+delegateExecution.getEventName());
        System.out.println("-------事件id------"+delegateExecution.getCurrentActivityId());

    }
}
