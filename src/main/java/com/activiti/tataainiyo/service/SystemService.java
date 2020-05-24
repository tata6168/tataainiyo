package com.activiti.tataainiyo.service;

import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SystemService {
    List<String> allUrl(HttpServletRequest request);
    List<Menu> urlAllocationMenuRolePermission(HttpServletRequest request);
    List<Permission> urlAllocationPermission(HttpServletRequest request);
    void shiroPermissionDelete();
}
