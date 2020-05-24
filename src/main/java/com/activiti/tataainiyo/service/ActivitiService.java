package com.activiti.tataainiyo.service;

import com.activiti.tataainiyo.demo.CompleteTask;
import com.activiti.tataainiyo.demo.StartProcessDef;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;
import java.util.Map;

public interface ActivitiService {

    List<ProcessDefinition> selectAllProcDef();

    void roleInsertProc(List<StartProcessDef> processDefList);

    void startProc(String procDefId, Map<String,Object> map);

    void completeTask(String taskId,Map<String,Object> map);

    List<CompleteTask> roleByProc(Long userId);
}
