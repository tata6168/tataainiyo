package com.activiti.tataainiyo.service.impl;

import com.activiti.tataainiyo.base.Query;
import com.activiti.tataainiyo.base.service.impl.BaseServiceImpl;
import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.mapper.MenuMapper;
import com.activiti.tataainiyo.service.MenuService;
import com.activiti.tataainiyo.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    //添加菜单
    public void insert(Menu menu) {
        menuMapper.insert(menu);
        menu.getChildren().forEach(e->e.setParentId(menu.getMenuId()));
    }
    //查询当前用户匹配的菜单
    @Override
    public List<Menu> selectPage(Query<Menu> query) {
        return super.selectPage(query);
    }

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

    @Override
    public void urlAllocationMenu(List<Menu> menuList) {
        //先添加父节点
        menuList.forEach(e->{
            menuMapper.insert(e);
            //为每一个子节点添加parentId
            e.getChildren().forEach(c->{
                c.setParentId(e.getMenuId());
            });
            //添加子节点
            menuMapper.urlAllocationMenu(e.getChildren());
        });
    }

    @Override
    public List<Menu> userByMenu(Long userId) {
        return menuMapper.userByMenu(userId);
    }
}
