package com.activiti.tataainiyo.service;
import com.activiti.tataainiyo.demo.Role;
import  com.activiti.tataainiyo.demo.User;
import com.activiti.tataainiyo.base.service.BaseService;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;

public interface UserService extends BaseService<User> {
    List<Role> userByRole(Long userId);

    void addUserRole(User user);
}
