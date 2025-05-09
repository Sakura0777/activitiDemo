package com.example.activitidemo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 使用 Activiti 这种工作流框架大致都分为以下几个步骤：
* 流程定义 -》 部署流程定义  -》 启动流程实例  -》  查询当前用户的待办任务  -》 完成任务*/
public class ActivitiTestExclusive {
   /* key-->  "leaveApplication" 是 xml文件定义的process id
      <process id="leaveApplication" name="请假流程定义" isExecutable="true">*/
    String key = "leaveProcessExclusive";
   public static String instanceId;

    public static String getInstanceId() {
        return instanceId;
    }

    public static void setInstanceId(String instanceId) {
        ActivitiTestExclusive.instanceId = instanceId;
    }

    /**
     * 生成Activiti的相关的表结构
     */
    @Test
    public void genActiviti(){
        // 使用classpath下的activiti.cfg.xml中的配置来创建 ProcessEngine对象
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();//取默认的流程引擎实例
        System.out.println(engine);
    }

    /*
    *  部署流程--RespositoryService
    */
    @Test
    public void testDeploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /*RepositoryService：提供流程定义和部署等功能。比如说，实现流程的的部署、删除，暂停和激活以及流程的查询等功能*/
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy =  repositoryService.createDeployment()
                .addClasspathResource("bpmn/leaveProcessExclusive.bpmn20.xml").name("排他网关请假申请流程定义").deploy();

        System.out.println("流程部署id：{}"+deploy.getId());
        System.out.println("流程部署id：{}"+deploy.getName());
    }
    /*
    启动流程--RuntimeService
    */
    @Test
    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /*RuntimeService：提供了处理流程实例不同步骤的结构和行为。包括启动流程实例、暂停和激活流程实例等功能*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object>map = new HashMap<>();

        map.put("assignee0","提莫");
        map.put("assignee1","维迦");
        map.put("assignee2","小炮");
        map.put("assignee3","波比");

        /*
         map 则是为其启动之后的流程实例赋值*/
        /*
        启动流程时，分别指定各个任务的负责人，上述代码表示该请假流程是由提莫发起的，审批人是维迦
        由于启动一个流程，就表示是流程实例被运行起来了，那么，就使用 RuntimeService 的 startProcessInstanceByKey() 方法进行，
        其中参数 key 表示指定所启动的流程定义，而 map 则是为其启动之后的流程实例赋值
        启动一个流程实例，我们的预期就是完成 Start event 事件，流程进入到提莫的“创建申请”这个步骤*/
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,map);
        System.out.println("流程定义id:"+instance.getProcessDefinitionId());
        /*流程实例id 后续查询任务也可以通过这个流程实例id查询  taskService.createTaskQuery().processInstanceId(instanceId).*/
        System.out.println("流程实例id:"+instance.getId());
        setInstanceId(instance.getId());
        System.out.println("流程实例id:-----"+getInstanceId());
        System.out.println("当前活动的id:"+instance.getActivityId());
    }

    /*查询个人待执行的人物--TaskService*/
    @Test
    public void testFindPersonalTaskList(){
        String assignee="提莫";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /*TaskService：提供有关任务相关功能的服务。包括任务的查询、删除以及完成等功能*/
        TaskService taskService = processEngine.getTaskService();
/*通过 TaskService 进行查询，分别通过请假流程的 key 和用户名称，查询该用户名下的所有任务*/
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(assignee).list();
        /*通过流程实例id查询任务*/
//        List<Task> taskList = taskService.createTaskQuery().processInstanceId(getInstanceId()).list();
        for (Task task:taskList){
            String taskId = task.getId();
            System.out.println("流程实例id:"+task.getProcessInstanceId());
            System.out.println("任务id:"+taskId);
            System.out.println("任务负责人："+task.getAssignee());
            System.out.println("任务名称："+task.getName());
            System.out.println("请假人："+taskService.getVariable(taskId,"leaveUser"));
            System.out.println("请假开始时间："+taskService.getVariable(taskId,"startDateTime"));
            System.out.println("请假结束时间："+taskService.getVariable(taskId,"endDateTime"));
            System.out.println("请假天数："+taskService.getVariable(taskId,"day"));
            System.out.println("请假理由："+taskService.getVariable(taskId,"reason"));

        }
    }

    @Test
    public void applyLeave(){
        String assignee = "提莫";
        String startDateTime = "2025/05/07 8:30";
        String endDateTime = "2025/05/09 17:30";
        String leaveReason = "考驾照";

        Map<String,Object>map = new HashMap<String,Object>();
        map.put("leaveUser",assignee);
        map.put("startDateTime",startDateTime);
        map.put("endDateTime",endDateTime);
        map.put("reason",leaveReason);
        map.put("day",2);


        /*完成任务*/
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();


        Task task = taskService.createTaskQuery().processDefinitionKey(key)
                .taskAssignee(assignee).singleResult();
        System.out.println("getInstanceId()"+ getInstanceId());


        taskService.complete(task.getId(),map);
    }

    @Test
    public void managerApprove(){
        String assignee = "维迦";


        /*完成任务*/
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(assignee).list();

        for (Task task:taskList){
            Map<String, Object> currentVariables = runtimeService.getVariables(task.getProcessInstanceId());
            System.out.println("当前流程变量: " + currentVariables);
            currentVariables.put("managerUser",assignee);
            currentVariables.put("approveStatus","通过");
            currentVariables.put("managerOpinion","此次请假不影响工作，同意请假，祝顺利");
            taskService.complete(task.getId(),currentVariables);

        }

    }



    @Test
public void directorApprove() {
    String assignee = "小炮";

    /*完成任务*/
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    TaskService taskService = processEngine.getTaskService();
    RuntimeService runtimeService = processEngine.getRuntimeService();
    List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(assignee).list();

    for (Task task : taskList) {
        Map<String, Object> currentVariables = runtimeService.getVariables(task.getProcessInstanceId());
        System.out.println("当前流程变量: " + currentVariables);
        currentVariables.put("directorUser", assignee);
        currentVariables.put("directorStatus", "同意");
        currentVariables.put("directorOpinion", "同意");
        taskService.complete(task.getId(), currentVariables);
        System.out.println("部门总监审批完成...");
    }
}
    @Test
    public void financeApprove() {
        String assignee = "波比";

        /*完成任务*/
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(assignee).list();

        for (Task task : taskList) {
            Map<String, Object> currentVariables = runtimeService.getVariables(task.getProcessInstanceId());
            System.out.println("当前流程变量: " + currentVariables);
            taskService.complete(task.getId(), currentVariables);
            System.out.println("请假审批完成...");
        }
    }
}
