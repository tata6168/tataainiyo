package com.activiti.tataainiyo;

import com.activiti.tataainiyo.demo.CompleteTask;
import com.activiti.tataainiyo.demo.StartProcessDef;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {
    public static final ProcessEngine PROCESS_ENGINE;
    private static RuntimeService runtimeService;
    private static RepositoryService repositoryService;
    private static TaskService taskService;
    private static HistoryService historyService;
    private static StartProcessDef processDef= new StartProcessDef();
    private static CompleteTask completeTask = new CompleteTask();
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
//.1查询流程procdef
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        list.forEach(e->{
            processDef.setProcDefId(e.getId());
            processDef.setProcName(e.getName());
        });
        //.2开启流程,并附带消息
        Map<String, Object> variable = new HashMap<>();
        //variable.put("start","开始");
        //runtimeService.startProcessInstanceById(processDef.getProcDefId(), variable);
// 3.查询待审批任务(拿到当前任务所携带的信息【variable】/携带条件完成任务)
        //Map<String, Object> map = new HashMap<>();
        //map.put("bossDecision","yes");
        //map.put("boss","驳回");
        //completeTask("manager",null,false);
        //procStatus("45001");
//4.查询
        //流程历史详情
        //procStatus("45001");
        //流程状态
        //System.out.println("47501"+historySearch("47501"));
        //System.out.println("45001"+historySearch("45001"));
//5.获取活动节点
        //activityEvent("45001");
        taskService.createTaskQuery().
        orderByTaskCreateTime().
        taskAssignee("manager").
        desc().list().forEach(t->{
            currentOutFlow(t.getId());
        });
    }

    //查看流程历史信息 47501
    public static void procStatus(String preInstId){
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceEndTime()
                .processInstanceId(preInstId)
                .desc().list();
        list.forEach(a->{
            System.out.println("活动名："+a.getActivityName());
            System.out.println("assignee:"+a.getAssignee());
            historyService.createHistoricVariableInstanceQuery().
                    processInstanceId(preInstId).
                    list().forEach(v->{
                System.out.println(v);
            });
        });
    }
    public static void activityEvent(String procInstId){
        Execution execution = runtimeService.createExecutionQuery().
                processInstanceId(procInstId).onlyChildExecutions().singleResult();
        System.out.println("activityId:"+execution.getActivityId());
        System.out.println("activityName:"+execution.getName());
        System.out.println("executionId:"+execution.getId());
    }

    //查询任务状态
    public static String historySearch(String procInstId){
        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        if(instance==null)
            return "不存在";
        if(instance.getEndTime()==null)
            return "任务已结束。。";
        return "任务待审批。。";
    }
    //处理任务
    public static void completeTask(String assignee,Map<String,Object> info,boolean select){
        //通过assignee查询到任务
        List<Task> taskList = taskService.createTaskQuery().orderByTaskAssignee().taskAssignee(assignee).desc().list();
        System.out.println("======================"+taskList.size());
        taskList.forEach(t->{
            System.out.println("taskInfo:");
            //获取任务消息
            Map<String, Object> variables = taskService.getVariables(t.getId());
            if(variables.size()!=0)
                variables.entrySet().forEach(v-> System.out.println(v.getKey()+":"+v.getValue()));
            //审批任务
            if(select) {
                Map<String, Object> condition = new HashMap<>();
                condition.put(assignee + "Decision", info.get(assignee + "Decision"));
                info.remove(assignee + "Decision");
                if(info!=null && info.size()!=0)
                    taskService.setVariables(t.getId(), info);
                taskService.complete(t.getId(), condition);
            }
        });
    }
    //查看当前节点
    public static void currentOutFlow(String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).onlyChildExecutions().singleResult();
        FlowNode node = (FlowNode) bpmnModel.getFlowElement(execution.getActivityId());
        List<SequenceFlow> outFlows = node.getOutgoingFlows();
        outFlows.forEach(o->{
            System.out.println("id:"+o.getId());
            System.out.println("name:"+o.getName());
            String conditionExpression = o.getConditionExpression();
            System.out.println("Condition:  "+o.getConditionExpression());
        });

    }
    //查看节点出路
    public void searchOutFlow(String taskId){
        //获取任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //历史流程实例
        HistoricProcessInstance  historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //流程定义
        ProcessDefinitionEntity deployedProcessDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
        //查询历史节点
        List<HistoricActivityInstance> historicActivityInstances = historyService.
                createHistoricActivityInstanceQuery().
                processInstanceId(task.getProcessInstanceId()).
                orderByProcessInstanceId().
                asc().list();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(deployedProcessDefinition.getId());
        //已执行历史节点
        List<String> activityIdyList = new ArrayList<>();
        historicActivityInstances.forEach(h->activityIdyList.add(h.getActivityId()));
        //已执行flow集合
        List<String> strings = historyFlowName(bpmnModel, deployedProcessDefinition, historicActivityInstances);
        strings.forEach(e->{
            System.out.println(e);
        });


    }
    public List<String> historyFlowName(BpmnModel bpmn,
                                        ProcessDefinitionEntity entity,
                                        List<HistoricActivityInstance> historicActivityInstanceList
                                        ){
        List<String> outName = new ArrayList<>();
        for (int i = 0; i < historicActivityInstanceList.size()-1; i++) {
            HistoricActivityInstance instance = historicActivityInstanceList.get(i);
            FlowNode flowNode = (FlowNode) bpmn.getFlowElement(instance.getActivityId());
            List<SequenceFlow> flows = flowNode.getOutgoingFlows();
            if(flows.size()>1){
                HistoricActivityInstance instance1 = historicActivityInstanceList.get(i + 1);
                flows.forEach(f->{
                    if(f.getTargetFlowElement().getId().equals(instance1.getActivityId())){
                        outName.add(f.getName());
                    }
                });
            }else {
                outName.add(flows.get(0).getName());
            }
        }
        return outName;
    }
    private static List<String> executedFlowIdList(BpmnModel bpmnModel,
                                                   ProcessDefinitionEntity processDefinitionEntity,
                                                   List<HistoricActivityInstance> historicActivityInstanceList) {

        List<String> executedFlowIdList = new ArrayList<>();
        for(int i=0;i<historicActivityInstanceList.size()-1;i++) {
            HistoricActivityInstance hai = historicActivityInstanceList.get(i);
            FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(hai.getActivityId());
            List<SequenceFlow> sequenceFlows = flowNode.getOutgoingFlows();
            if(sequenceFlows.size()>1) {
                HistoricActivityInstance nextHai = historicActivityInstanceList.get(i+1);
                sequenceFlows.forEach(sequenceFlow->{
                    if(sequenceFlow.getTargetFlowElement().getId().equals(nextHai.getActivityId())) {
                        executedFlowIdList.add(sequenceFlow.getName());
                    }
                });
            }else {
                executedFlowIdList.add(sequenceFlows.get(0).getName());
            }
        }

        return executedFlowIdList;
    }
    private static InputStream generateProcessDiagram(ProcessEngine processEngine, String processInstanceId) {
        // TODO Auto-generated method stub
        //获取历史流程实例
        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        //获取历史流程定义
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl)
                processEngine.getRepositoryService()).getDeployedProcessDefinition(historicProcessInstance
                .getProcessDefinitionId());
        //查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = processEngine.getHistoryService().
                createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
        //已执行历史节点
        List<String> executedActivityIdList = new ArrayList<String>();
        historicActivityInstanceList.forEach(historicActivityInstance->{executedActivityIdList.add(historicActivityInstance.getActivityId());});

        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionEntity.getId());
        //已执行flow的集和
        List<String> executedFlowIdList = executedFlowIdList(bpmnModel,
                processDefinitionEntity,
                historicActivityInstanceList);

        ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        InputStream diagram = processDiagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList,executedFlowIdList, "黑体","黑体","黑体",null,1.0);

        return diagram;
    }


}
