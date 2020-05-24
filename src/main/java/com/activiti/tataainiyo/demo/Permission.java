package com.activiti.tataainiyo.demo;

import com.activiti.tataainiyo.base.BaseDemo;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Permission extends BaseDemo {
    private Long permissionId;
    private String permissionName;
    private String path;
    private String sn;
}
