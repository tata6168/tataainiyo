package com.activiti.tataainiyo.mapper;

import com.activiti.tataainiyo.base.BaseMapper;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {
    void insertBatch(List<Role> roleList);
    void insertRoleByPermission(Map<String, Object> role);
    void insertRoleByMenu(Map<String,Object> role);
    List<Role> selectAll();
    List<Permission> selectPermission(Integer roleId);
    void deletePermission(Long roleId);
    void deleteMenu(Long roleId);
}
