package com.activiti.tataainiyo.service.impl;
import com.activiti.tataainiyo.demo.CompleteTask;
import com.activiti.tataainiyo.demo.StartProcessDef;
import com.activiti.tataainiyo.mapper.ActivitiMapper;
import com.activiti.tataainiyo.service.ActivitiService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    private ActivitiMapper activitiMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public List<ProcessDefinition> selectAllProcDef() {
        return repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey()
                .desc().list();
    }

    @Override
    public void roleInsertProc(List<StartProcessDef> processDefList) {
         activitiMapper.roleInsertProc(processDefList);
    }

    @Override
    public void startProc(String procDefId,Map<String,Object> map) {
        runtimeService.startProcessInstanceById(procDefId,map);
    }

    @Override
    public void completeTask(String taskId,Map<String,Object> map) {
        taskService.complete(taskId,map);
    }

    @Override
    public List<CompleteTask> roleByProc(Long userId) {
        return activitiMapper.roleByProc(userId);
    }

}