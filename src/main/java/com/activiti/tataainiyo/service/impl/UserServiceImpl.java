package com.activiti.tataainiyo.service.impl;

import com.activiti.tataainiyo.base.service.impl.BaseServiceImpl;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.demo.User;
import com.activiti.tataainiyo.mapper.UserMapper;
import com.activiti.tataainiyo.service.UserService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<Role> userByRole(Long userId) {
        return userMapper.userByRole(userId);
    }


    @Override
    public void addUserRole(User user) {
        userMapper.addUserRole(user);
    }
}
