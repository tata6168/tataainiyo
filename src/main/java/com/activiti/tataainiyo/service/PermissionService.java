package com.activiti.tataainiyo.service;

import com.activiti.tataainiyo.base.service.BaseService;
import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.demo.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission> {
    void allocationUrlPermission(List<Permission> permissionList);

    List<Permission> selecAll();
}
