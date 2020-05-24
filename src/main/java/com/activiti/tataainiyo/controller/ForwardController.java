package com.activiti.tataainiyo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forward")
public class ForwardController {
    @RequestMapping("/task")
    public String task(){
        return "/component/task/task";
    }
    @RequestMapping("/tata")
    public String main(){
        return "/main";}
}
