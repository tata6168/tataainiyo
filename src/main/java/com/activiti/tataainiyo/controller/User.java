package com.activiti.tataainiyo.controller;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class User {
    Integer id;
    String name;
    Integer age;
    List<User> userList = new ArrayList<>();
}
