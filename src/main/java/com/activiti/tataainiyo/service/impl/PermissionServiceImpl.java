package com.activiti.tataainiyo.service.impl;

import com.activiti.tataainiyo.base.service.impl.BaseServiceImpl;
import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.mapper.PermissionMapper;
import com.activiti.tataainiyo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void allocationUrlPermission(List<Permission> permissionList) {
        permissionMapper.allocationUrlPermission(permissionList);
    }

    @Override
    public List<Permission> selecAll() {
        return permissionMapper.selectAll();
    }

}
