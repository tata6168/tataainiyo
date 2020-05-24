package com.activiti.tataainiyo.service.impl;

import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.mapper.PermissionMapper;
import com.activiti.tataainiyo.mapper.UserMapper;
import com.activiti.tataainiyo.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

}
