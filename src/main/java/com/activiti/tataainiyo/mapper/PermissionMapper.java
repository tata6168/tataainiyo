package com.activiti.tataainiyo.mapper;

import com.activiti.tataainiyo.base.BaseMapper;
import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.User;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    void allocationUrlPermission(List<Permission> permissionList);
    List<Permission> selectAll();
}
