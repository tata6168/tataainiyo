package com.activiti.tataainiyo.service.impl;

import com.activiti.tataainiyo.demo.Menu;
import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.mapper.SystemMapper;
import com.activiti.tataainiyo.service.MenuService;
import com.activiti.tataainiyo.service.PermissionService;
import com.activiti.tataainiyo.service.RoleService;
import com.activiti.tataainiyo.service.SystemService;
import com.activiti.tataainiyo.util.UrlUtil;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class SystemServiceImpl implements SystemService {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SystemMapper systemMapper;
    @Override//获得所有controller路径
    public List<String> allUrl(HttpServletRequest request){
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null)
            return null;
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //获取RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                HandlerMapping.class, true, false);
        List<String> urlList = new ArrayList<>();
        for (HandlerMapping handlerMapping:allRequestMappings.values()) {
            //本项目只需要RequestMappingHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = ((RequestMappingHandlerMapping) handlerMapping).getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : handlerMethods.entrySet()) {
                    Object[] url = methodEntry.getKey().getPatternsCondition().getPatterns().toArray();
                    urlList.add((String) url[0]);
                }
            }
        }
        return urlList;
    }
    @Override//以controller中的路径添加菜单 权限 角色
    @Transactional
    public List<Menu> urlAllocationMenuRolePermission(HttpServletRequest request){
        long l = System.currentTimeMillis();
        List<String> urlList = allUrl(request);
        Map<String, Integer>  indexTab= new HashMap<>();
        List<Menu> menuList = new ArrayList<>();
        List<Role> roleList = new ArrayList<>();
        urlList.forEach(e->{
            String prefix = UrlUtil.urlGetPrefix(e);
            Menu children = new Menu();
            Permission permission = new Permission();
            if(!indexTab.containsKey(prefix)){
                String sn = UrlUtil.confirmUrlType(e);
                //无父结点 创建父节点记录list中的index 并创建一个子节点
                Menu parent = new Menu();
                Role role = new Role();
                //菜单父节点
                parent.setLabel(prefix);
                //菜单子节点
                children.setPath(e);
                children.setLabel(sn);
                //菜单节点初始化完成
                parent.getChildren().add(children);
                menuList.add(parent);
                //角色添加
                role.setRoleName(prefix);
                //权限
                permission.setSn(sn);
                permission.setPath(e);
                //角色权限子节点完成
                role.getPermissionList().add(permission);
                roleList.add(role);
                //标记节点
                indexTab.put(prefix,menuList.size()-1);
            }else {
                String sn = UrlUtil.confirmUrlType(e);
                //有父节点直接追加即可
                children.setPath(e);
                children.setLabel(sn);
                menuList.get(indexTab.get(prefix)).getChildren().add(children);
                //有角色追加权限
                permission.setPath(e);
                permission.setSn(sn);
                roleList.get(indexTab.get(prefix)).getPermissionList().add(permission);
            }
        });
        //添加菜单
        menuService.urlAllocationMenu(menuList);
        //添加角色权限
        roleList.forEach(r->{
            //添加角色
            roleService.insert(r);
            r.getPermissionList().forEach(p->{
                //添加权限
                permissionService.insert(p);
                //添加中间表
                roleService.insertRoleByPermission(r.getRoleId(),p.getPermissionId());
            });
        });
        System.out.println("time:"+(System.currentTimeMillis()-l));
       return menuList;
    }

    @Override//以controller中的路径查询权限
    public List<Permission> urlAllocationPermission(HttpServletRequest request){
        List<String> urlList = allUrl(request);
        List<Permission> permissionList = new ArrayList<>();
        urlList.forEach(e->{
            Permission permission = new Permission();
            permission.setSn(UrlUtil.confirmUrlType(e));
            permission.setPath(e);
            permissionList.add(permission);
        });
        return permissionList;
    }

    @Override
    public void shiroPermissionDelete() {
        systemMapper.shiroPermissionDelete();
    }
}
