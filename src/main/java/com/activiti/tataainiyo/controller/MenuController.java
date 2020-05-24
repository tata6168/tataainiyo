package com.activiti.tataainiyo.controller;

import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController  {
    @Autowired
    MenuService menuService;
    @RequestMapping("/selectAll")
    @ResponseBody
    public List<Menu> selectAll(){
        return menuService.selectAll();
    }
    @RequestMapping("/userByMenu")
    @ResponseBody
    public List<Menu> userByMenu(Long userId){
        return menuService.userByMenu(userId);
    }
}
