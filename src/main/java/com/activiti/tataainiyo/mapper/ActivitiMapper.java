package com.activiti.tataainiyo.mapper;

import com.activiti.tataainiyo.demo.CompleteTask;
import com.activiti.tataainiyo.demo.StartProcessDef;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import java.util.List;

public interface ActivitiMapper {
    void roleInsertProc(List<StartProcessDef> processDefList);

    List<CompleteTask> roleByProc(Long userId);
}
