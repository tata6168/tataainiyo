package com.activiti.tataainiyo;

import com.activiti.tataainiyo.demo.Permission;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.demo.User;
import com.activiti.tataainiyo.mapper.MenuMapper;
import com.activiti.tataainiyo.mapper.RoleMapper;
import com.activiti.tataainiyo.mapper.UserMapper;
import com.activiti.tataainiyo.service.PermissionService;
import com.activiti.tataainiyo.service.SystemService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = TataainiyoApplication.class)
@RunWith(SpringRunner.class)
class TataainiyoApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleMapper roleMapper;
    @Test
    void contextLoads() {
        System.out.println(menuMapper.selectAll());
    }
    @Test
    void t1(){
        Role role = new Role();
        role.setRoleId(1000L);
        User user = new User();
        user.setUserId(999L);
        user.getRoleList().add(role);
        user.getRoleList().add(role);
        userMapper.addUserRole(user);
    }
    @Test
    void t2(){
        permissionService.selecAll().forEach(e-> System.out.println(e));
    }
    @Test
    void t3(){
        Map<String, Object> map = new HashMap<>();
        map.put("roleId",123456);
        Integer[] idS = {123,456,789};
        List<Integer> integers = Arrays.asList(idS);
        map.put("permissionList",integers);
        roleMapper.insertRoleByPermission(map);

    }
    @Test
    void t4(){
        redisTemplate.opsForValue().set("name","liuyan");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}
