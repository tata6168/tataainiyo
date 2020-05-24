package com.activiti.tataainiyo.mybatisIntercept;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*
* Signature:签名
* */
//@Component
@Intercepts({@Signature(
    type = ResultSetHandler.class,
    method = "handleResultSets",
    args = {Statement.class}
)})
public class MybatisIntercept implements Interceptor {
    //会先执行每个插件的plugin方法
    @Override
    public Object plugin(Object target) {
        //返回拦截对象
        return Plugin.wrap(target,this);
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement = (Statement) invocation.getArgs()[0];
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()){

        };
        //前代码
        Object proceed = invocation.proceed();
        //后代码
        return proceed;
    }



    @Override
    public void setProperties(Properties properties) {

    }
}
