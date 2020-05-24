package com.activiti.tataainiyo.demo;

import com.activiti.tataainiyo.base.BaseDemo;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@ToString
@Data
public class Role extends BaseDemo {
    private Long roleId;
    private String roleName;
    private int status;
    private List<Permission> permissionList=new ArrayList<>();
    private List<Menu> menuList = new ArrayList<>();
    private List<StartProcessDef> processDefList = new ArrayList<>();
    private List<CompleteTask> completeTasks = new ArrayList<>();
}
