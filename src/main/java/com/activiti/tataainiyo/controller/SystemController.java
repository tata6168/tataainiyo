package com.activiti.tataainiyo.controller;
import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.service.PermissionService;
import com.activiti.tataainiyo.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private PermissionService permissionService;
    //获取所有url
    @RequestMapping("/allUrl")
    @ResponseBody
    public List<String> allUrl(HttpServletRequest request){
       return systemService.allUrl(request);
    }
    //将所有url分配到menu permission（添加字段path，sn）表并管理role（roleName）表 roleName=（controller顶层mapping）
    @RequestMapping("/urlAllocationMenuRolePermission")
    @ResponseBody
    public List<Menu> urlAllocationMenuRolePermission(HttpServletRequest request){
        return systemService.urlAllocationMenuRolePermission(request);
    }
    @RequestMapping("/urlAllocationPermission")
    @ResponseBody
    public List<Permission> urlAllocationPermission(HttpServletRequest request){
        return systemService.urlAllocationPermission(request);
    }
    //清空 userbyroel/role/rolebypermission/rolebymenu/permission/menu/表
    @RequestMapping("/truncateSystemTable")
    @ResponseBody
    public void systemShiroPermissionDelete(){
        systemService.shiroPermissionDelete();
    }
    //创建超级管理员
    @RequestMapping("/createSuperManager")
    @ResponseBody
    public void createSuperManager(HttpServletRequest request){

    }
}
