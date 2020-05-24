package com.activiti.tataainiyo.controller;

import com.activiti.tataainiyo.demo.StartProcessDef;
import com.activiti.tataainiyo.service.ActivitiService;
import com.activiti.tataainiyo.service.RoleService;
import com.activiti.tataainiyo.service.impl.ActivitiServiceImpl;
import com.activiti.tataainiyo.util.JsonResult;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activiti")
public class ActivitiController {
    @Autowired
    private ActivitiService activitiService;
    //查询所有流程定义
    @RequestMapping("/selectAllProcDef")
    @ResponseBody
    public List<ProcessDefinition> selectAllProcDef(){
        return activitiService.selectAllProcDef();
    }
    //为角色添加流程启动
    @RequestMapping("/roleInsertProc")
    @ResponseBody
    public JsonResult roleInsertProc(List<StartProcessDef> processDefList){
        try{
            activitiService.roleInsertProc(processDefList);
            return JsonResult.success("操作成功",null);
        }catch (Exception e){
            return JsonResult.error(e.getMessage());
        }
    }
    //查询当前角色拥有的启动proc
    @RequestMapping("/roleByProc")
    @ResponseBody
    public JsonResult roleByProc(Long userId){
        try {
            activitiService.roleByProc(userId);
            return JsonResult.success("操作成功",null);
        }catch (Exception e){
            return JsonResult.error(e.getMessage());
        }
    }

    //启动流程
    @RequestMapping("/startProc")
    @ResponseBody
    public JsonResult startProc(String procDefId, Map<String,Object> map){
        try {
            activitiService.startProc(procDefId,map);
            return JsonResult.success("操作成功",null);
        }catch (Exception e){
            return JsonResult.error(e.getMessage());
        }
    }
    //查询当前角色可审批的任务
    //审批流程
    @RequestMapping("/completeTask")
    @ResponseBody
    public JsonResult completeTask(String taskId,Map<String,Object> map){
        try {
            activitiService.completeTask(taskId,map);
            return JsonResult.success("操作成功",null);
        }catch (Exception e){
            return JsonResult.error(e.getMessage());
        }
    }


}
