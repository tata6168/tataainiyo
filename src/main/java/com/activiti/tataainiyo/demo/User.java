package com.activiti.tataainiyo.demo;

import com.activiti.tataainiyo.base.BaseDemo;
import com.activiti.tataainiyo.util.MD5Util;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class User extends BaseDemo {
    private Long userId;
    private String userName;
    private String passWord;
    private Date registerTime;
    private List<Role> roleList = new ArrayList<>();
}
