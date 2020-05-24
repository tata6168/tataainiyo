package com.activiti.tataainiyo.demo;

import lombok.Data;

@Data
public class StartProcessDef {
    /*
    * procDefId:启动流程
    * procName:页面启动按钮名称
    * */
    private Integer roleId;
    private String procDefId;
    private String procName;
}
