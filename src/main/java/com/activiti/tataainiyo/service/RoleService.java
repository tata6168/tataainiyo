package com.activiti.tataainiyo.service;

import com.activiti.tataainiyo.base.service.BaseService;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoleService extends BaseService<Role> {
    void insertBatch(List<Role> roleList);

    void insertRoleByPermission(Long roleId,Long permissionId);

    List<Role> selectAll();


    List<Permission> selectPermission(Integer roleId);

    void updateRolePermission(Map<String, Object> role);

    void updateRoleMenu(Map<String, Object> map);
}
