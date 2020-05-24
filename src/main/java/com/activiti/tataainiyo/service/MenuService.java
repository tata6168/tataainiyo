package com.activiti.tataainiyo.service;

import com.activiti.tataainiyo.base.service.BaseService;
import com.activiti.tataainiyo.demo.Menu;

import java.util.List;


public interface MenuService extends BaseService<Menu> {
    List<Menu> selectAll();
    void urlAllocationMenu(List<Menu> menuList);

    List<Menu> userByMenu(Long userId);
}
