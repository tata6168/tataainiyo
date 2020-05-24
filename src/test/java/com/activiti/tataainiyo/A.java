package com.activiti.tataainiyo;

import com.activiti.tataainiyo.base.BaseDemo;
import com.activiti.tataainiyo.base.Query;

public class A {
    public static void main(String[] args) {
        Query<BaseDemo> query = new Query<>();
        query.setCurrentPage(9l);
        query.setPageSize(null);
        System.out.println(query);
    }
}
