package com.activiti.tataainiyo.controller;

import com.activiti.tataainiyo.demo.User;
import com.activiti.tataainiyo.util.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ShiroController {
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(User user){
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUserName(),user.getPassWord()));
        }catch (UnknownAccountException e) {
            return  new JsonResult(false,"用户名出错");
        } catch (IncorrectCredentialsException e) {
            return  new JsonResult(false,"密码错误");
        }catch (AuthenticationException e) {
            return  new JsonResult(false,"神秘错误");
        }
        return JsonResult.success("登录成功",null);
    }
    @RequestMapping("/success")
    public String success(){
        return "/main";
    }
    @RequestMapping("/logout")
    public void logOut(){
        SecurityUtils.getSubject().logout();
    }
}
