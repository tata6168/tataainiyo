package com.activiti.tataainiyo.controller;
import com.activiti.tataainiyo.base.Query;
import com.activiti.tataainiyo.demo.Role;
import com.activiti.tataainiyo.demo.User;
import com.activiti.tataainiyo.service.UserService;
import com.activiti.tataainiyo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("/insert")
    @ResponseBody
    public void registerUser(User user){
        userService.insert(user);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public void delete(Long id){
        userService.delete(id);
    }
    @RequestMapping("/update")
    @ResponseBody
    public void update(User user){
        userService.update(user);
    }
    @RequestMapping("/selectOneById/{id}")
    @ResponseBody
    public User selectOneById(@PathVariable("id") Long id){
        return userService.selectOneById(id);
    }
    @RequestMapping("/selectPage")
    @ResponseBody
    public JsonResult selectPage(Query<User> query, HttpServletResponse response){
        return JsonResult.success(userService.selectPage(query),userService.count());
    }
    @RequestMapping("/userByRole")
    @ResponseBody
    public List<Role> userByRole(Long userId){
        return userService.userByRole(userId);
    }
    @RequestMapping(value = "/addUserRole",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addUserRole(@RequestBody User user){
        try {
            userService.addUserRole(user);
        }catch (Exception e){
            return JsonResult.error("操作失败！");
        }
        return JsonResult.success("角色更新成功!",null);
    }
}
