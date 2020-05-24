package com.activiti.tataainiyo.base.service.impl;

import com.activiti.tataainiyo.base.BaseDemo;
import com.activiti.tataainiyo.base.BaseMapper;
import com.activiti.tataainiyo.base.Query;
import com.activiti.tataainiyo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class BaseServiceImpl<T extends BaseDemo> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;


    @Override
    public Long count() {
        return baseMapper.count();
    }

    @Override
    public void insert(T t) {
        baseMapper.insert(t);
    }

    @Override
    public void delete(Long id) {
        baseMapper.delete(id);
    }

    @Override
    public void update(T t) {
        baseMapper.update(t);
    }

    @Override
    public T selectOneById(Long id) {
        return baseMapper.selectOneById(id);
    }

    @Override
    public List<T> selectPage(Query<T> query) {
        return baseMapper.selectPage(query);
    }
}
