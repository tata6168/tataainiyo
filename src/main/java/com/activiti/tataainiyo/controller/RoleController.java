package com.activiti.tataainiyo.controller;

import com.activiti.tataainiyo.base.Query;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.service.RoleService;
import com.activiti.tataainiyo.util.JsonResult;
import org.jcp.xml.dsig.internal.dom.ApacheOctetStreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/insert")
    @ResponseBody
    public void insert(Role role){
        roleService.insert(role);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public void delete(Long id){
        roleService.delete(id);
    }
    @RequestMapping("/update")
    @ResponseBody
    public void update(Role role){
        roleService.update(role);
    }
    @RequestMapping("/selectOneById")
    @ResponseBody
    public Role selectOneById(Long id){
        return roleService.selectOneById(id);
    }
    @RequestMapping("/selectPage")
    @ResponseBody
    public List<Role> selectPage(Query<Role> query){
        return roleService.selectPage(query);
    }
    @RequestMapping("/selectAll")
    @ResponseBody
    public List<Role> selectAll(){return roleService.selectAll();}

    @RequestMapping(value = "/updateRolePermission",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateRolePermission(@RequestBody Map<String,Object> map){
        roleService.updateRolePermission(map);
        return JsonResult.success(true,"操作成功...");
    }
    @RequestMapping(value = "/updateRoleMenu",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateRoleMenu(@RequestBody Map<String,Object> map){
        roleService.updateRoleMenu(map);
        return JsonResult.success(true,"操作成功");
    }

    @RequestMapping("/selectPermission")
    @ResponseBody
    public List<Permission> selectPermission(Integer roleId){
        return roleService.selectPermission(roleId);
    }
}
