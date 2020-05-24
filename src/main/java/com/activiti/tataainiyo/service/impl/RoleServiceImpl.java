package com.activiti.tataainiyo.service.impl;

import com.activiti.tataainiyo.base.service.impl.BaseServiceImpl;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.mapper.RoleMapper;
import com.activiti.tataainiyo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public void insertBatch(List<Role> roleList) {
        roleMapper.insertBatch(roleList);
    }

    @Override
    public void insertRoleByPermission(Long roleId,Long permissionId) {

    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Permission> selectPermission(Integer roleId) {
        return roleMapper.selectPermission(roleId);
    }

    @Override
    @Transactional
    /*
    * map=>KEY roleId [Long] / permissionList [ArrayList]
    * 以上为 键名 值类型
    * */
    public void updateRolePermission(Map<String, Object> role) {
        roleMapper.deletePermission(Long.parseLong(role.get("roleId").toString()));
        //为空时直接删除，不进行插入操作
        if(role.containsKey("permissionList"))
            roleMapper.insertRoleByPermission(role);
    }

    @Override
    @Transactional
    public void updateRoleMenu(Map<String, Object> map) {
        roleMapper.deletePermission(Long.parseLong(map.get("roleId").toString()));
        if(map.containsKey("menuList"))
            roleMapper.insertRoleByPermission(map);
    }
}
