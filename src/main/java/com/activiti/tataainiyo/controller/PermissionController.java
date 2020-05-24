package com.activiti.tataainiyo.controller;

import com.activiti.tataainiyo.base.Query;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.service.PermissionService;
import com.activiti.tataainiyo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult registerPermission(@RequestBody Permission permission){
        permissionService.insert(permission);
        return JsonResult.success(true,"success");
    }
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(Long permissionId){
        permissionService.delete(permissionId);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public void update(@RequestBody Permission permission){
        permissionService.update(permission);
    }
    @RequestMapping("/selectOneById")
    @ResponseBody
    public Permission selectOneById(Long id){
        return permissionService.selectOneById(id);
    }
    @RequestMapping("/selectPage")
    @ResponseBody
    public JsonResult selectPage(Query<Permission> query){
        return JsonResult.success(permissionService.selectPage(query),permissionService.count());
    }
    @RequestMapping("/selectAll")
    @ResponseBody
    public List<Permission> selectAll(){
        return permissionService.selecAll();
    }
}
