package com.activiti.tataainiyo.mapper;

import com.activiti.tataainiyo.base.BaseMapper;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.demo.User;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    User authenticationName(String userName);
    List<Role> userByRole(Long userId);
    void addUserRole(User user);

}
