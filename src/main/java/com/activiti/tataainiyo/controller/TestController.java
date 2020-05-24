package com.activiti.tataainiyo.controller;

import com.activiti.tataainiyo.util.JsonResult;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/map")
    @ResponseBody
    public void map(@RequestParam Map<String,Object> map){
        System.out.println(map);
    }
    @RequestMapping("/result")
    @ResponseBody
    public JsonResult result(){

        return JsonResult.success(true,"operation success...");
    }
    @RequestMapping("/path/{name}/{age}")
    @ResponseBody
    public void pathVariable(User user){
        System.out.println(user);
    }
    @RequestMapping(value = "/paramBody",method = RequestMethod.POST)
    @ResponseBody
    public void paramAndBody(@RequestParam String test,User param,@RequestBody User body){
        System.out.println("test:"+test);
        System.out.println("param:"+param);
        System.out.println("body:"+body);
    }
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public void post(@RequestBody User user){
        System.out.println(user);
    }

}
