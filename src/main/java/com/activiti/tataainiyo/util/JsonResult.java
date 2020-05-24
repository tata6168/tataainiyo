package com.activiti.tataainiyo.util;

import lombok.Data;

import java.util.List;

/**
 * @ClassName JsonResult
 * @Description
 * @Author Tian
 * @Date 2019/9/25 10:19
 * @Version 1.0
 */
@Data
public class JsonResult {
    private boolean state;
    private String msg;
    private long count;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private JsonResult(boolean state, String msg, Object data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
        if (data instanceof List) {
            this.count = ((List) data).size();
        }
    }

    public JsonResult(boolean state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public JsonResult(boolean state, String msg, long count, Object data) {
        this.state = state;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public JsonResult(boolean state) {
        this.state = state;
    }

    public JsonResult() {

    }

    public static JsonResult success(boolean statue,String message) {
        return new JsonResult(true,message);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(true, null, data);
    }

    public static JsonResult success(Object data, long count) {
        return new JsonResult(true, null, count, data);
    }

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(true, msg, data);
    }

    public static JsonResult error() {
        return new JsonResult(false);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(false, msg);
    }

    public static JsonResult error(Object data) {
        return new JsonResult(false, null, data);
    }

    public static JsonResult error(String msg, Object data) {
        return new JsonResult(false, msg, data);
    }

    /**
     * 自定义
     *
     * @param state
     * @return
     */
    public static JsonResult customize(boolean state, String msg, Object data) {
        return new JsonResult(state, msg, data);
    }

    public static JsonResult autoState(boolean state) {
        return new JsonResult(state);
    }
}
