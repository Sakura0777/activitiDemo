package com.example.activitidemo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;

public class suspendActiviti {
    @Test public void suspend(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("leaveApplication").list();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("leaveApplication:3:177504").singleResult();
//        for(ProcessDefinition processDefinition:processDefinitionList){
            boolean suspended = processDefinition.isSuspended();
            String definitionId = processDefinition.getId();
            if(suspended){
                repositoryService.activateProcessDefinitionById(definitionId,
                        true,null);
                System.out.println("流程定义id"+definitionId+"已激活");
            } else {
                repositoryService.suspendProcessDefinitionById(definitionId,true,null);
                System.out.println("流程定义id"+definitionId+"已挂起");
            }
//        }

    }

}
