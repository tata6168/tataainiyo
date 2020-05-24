package com.activiti.tataainiyo.mapper;

import com.activiti.tataainiyo.base.BaseMapper;
import com.activiti.tataainiyo.demo.Menu;

import java.util.List;
import java.util.Map;


public interface MenuMapper extends BaseMapper<Menu> {
    //添加父节点
    void insertParent(Menu menu);
    //添加
    void urlAllocationMenu(List<Menu> menuList);
    //查询全部
    List<Menu> selectAll();

    List<Menu> userByMenu(Long userId);
}
