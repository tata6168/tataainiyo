package com.activiti.tataainiyo.base;

import java.util.List;

public interface BaseMapper<T extends BaseDemo> {
    void insert(T t);
    void delete(Long id);
    void update(T t);
    Long count();
    T selectOneById(Long id);
    List<T> selectPage(Query<T> query);
}
