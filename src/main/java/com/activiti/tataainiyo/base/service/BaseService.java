package com.activiti.tataainiyo.base.service;

import com.activiti.tataainiyo.base.BaseDemo;
import com.activiti.tataainiyo.base.Query;

import java.util.List;

public interface BaseService<T extends BaseDemo> {
    Long count();
    void insert(T t);
    void delete(Long id);
    void update(T t);
    T selectOneById(Long id);
    List<T> selectPage(Query<T> query);
}
