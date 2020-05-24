package com.activiti.tataainiyo;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.activiti.engine.*;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import sun.net.www.content.image.png;

import java.util.*;

public class ActivitiTest {
    public static final ProcessEngine PROCESS_ENGINE;
    private static RuntimeService runtimeService;
    private static RepositoryService repositoryService;
    private static TaskService taskService;
    private static HistoryService historyService;
    private static String procDefinitionId;
    private static String taskId;
    static{
        ProcessEngineConfiguration processEngin = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        processEngin.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngin.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activity?serverTimezone=UTC&nullCatalogMeansCurrent=true");
        processEngin.setJdbcUsername("root");
        processEngin.setJdbcPassword("123456");
        /*
         * TRUE:存在就使用，不存在就创建
         * FALSE:存在就使用，不存在就抛出异常
         * DROP:先删除再创建
         * */
        processEngin.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //创建processEngine
        PROCESS_ENGINE = processEngin.buildProcessEngine();
        repositoryService=processEngin.getRepositoryService();
        runtimeService=processEngin.getRuntimeService();
        taskService=processEngin.getTaskService();
        historyService=processEngin.getHistoryService();
    }
    public static void main(String[] args) {
        //1.部署文件
       Deployment deployment = deployment("processes/leave.bpmn", "processes/leave.png");
//      repositoryService.createDeploymentQuery().list().forEach(e->{
//            deleteDeployment(e.getId(),true);
//        });
//        System.out.println(deployment.getId());
//        deleteDeployment("10001",true);
        //2.查询流程
        repositoryService.createProcessDefinitionQuery().latestVersion().list().forEach(e->{
            procDefinitionId = e.getId();
            System.out.println(e.getName()); //任务名字
            System.out.println(e.getId());  //启动任务(版本可以确定版本，存入table)
            System.out.println(e.getKey()); //启动任务
        });
        //3.启动流程(一般用processesInstance中的key)
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefinitionId);
        //4.查询需要审批的任务
       List<Task> taskInfoS = taskService.createTaskQuery()
               .orderByTaskCreateTime()
               .taskAssignee("manager")
               .desc().list();
        //5.带条件审批任务
        taskInfoS.forEach(e->{
            int i = taskInfoS.indexOf(e);
            if(i==0){
                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("managerDecision","yes");
                taskService.complete(e.getId(),stringObjectHashMap);
            }
        });


    }
    //complete
    public static void taskComplete(String taskId){
        taskService.complete(taskId);
    }
    //taskSelect
    public static List<Task> taskSelect(String assignee, String definitionKey){
        taskService.createTaskQuery().processDefinitionKey("").taskAssignee("").desc().list();
        return taskService.createTaskQuery()
                .taskAssignee(assignee)
                .processDefinitionKey(definitionKey)
                .orderByTaskCreateTime()
                .desc().list();
    }
    //executionSelect
    public static List<Execution> executionSelect(){
        List<Execution> list = runtimeService.createExecutionQuery()
                .orderByProcessInstanceId()
                .desc().list();
        list.forEach(e->{
            System.out.println("executionId:"+e.getId());
            System.out.println("executionName:"+e.getName());
            System.out.println("ProcInstId:"+e.getProcessInstanceId());
            System.out.println("ActivitiId:"+e.getActivityId());
            System.out.println("Description(描述):"+e.getDescription());
            System.out.println("parentId:"+e.getParentId());
            System.out.println("RootProcInstId:"+e.getRootProcessInstanceId());
            System.out.println("SuperExecutionId:"+e.getSuperExecutionId());
            System.out.println("Ended(结束):"+e.isEnded());
            System.out.println("Suspended(暂停的/缓期的):"+e.isSuspended());
        });
        return list;
    }
    //startDeployment
    public static void startProcess(String definitionKey){
        //execution key 方式启动最高版本
        runtimeService.startProcessInstanceByKey(definitionKey);
        //根据id 启动确定的数据
        //runtimeService.startProcessInstanceById("");
        //runtimeService.startProcessInstanceByKeyAndTenantId("","");
        /*根据message 启动 需要改动一下流程定义文件的startEvent，增加messageEventDefinition。
        最终其实还是会走到用processDefinitionId来启动。。。*/
        //runtimeService.startProcessInstanceByMessage("");
    }
    //processDefinition
    public static List<ProcessDefinition> procDefSelect(Set<String> deploymentIds){
        //两种查询方式
//        repositoryService.createProcessDefinitionQuery().processDefinitionIds(Set<String> set)
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .deploymentIds(deploymentIds)
                .desc().list();
        return list;
    }
    //deploymentSelect
    public static List<Deployment> deploymentSelect(){

        List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().list();
        list.forEach(e->{
            System.out.println("cateGory:"+e.getCategory());
            System.out.println("createTime:"+e.getDeploymentTime());
            System.out.println("Id:"+e.getId());
            System.out.println("Key:"+e.getKey());
            System.out.println("Name:"+e.getName());
            System.out.println("TenantId:"+e.getTenantId());
        });
        return list;
    }
    //processDeployment
    public static Deployment deployment(String bpmn,String png){
        DeploymentBuilder deployment = repositoryService.createDeployment();

        Deployment deploy = deployment.addClasspathResource(bpmn).addClasspathResource(png).deploy();
        return deploy;

    }
    //deleteDeployment
    public static void deleteDeployment(String deploymentId,boolean bn){
        repositoryService.deleteDeployment(deploymentId,bn);
    }
}
