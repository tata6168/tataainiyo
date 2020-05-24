package com.activiti.tataainiyo.demo;

import com.activiti.tataainiyo.base.BaseDemo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu extends BaseDemo {
    Integer menuId;
    Integer parentId;
    String label;//标签
    String path;
    List<Menu> children = new ArrayList<>();
}
