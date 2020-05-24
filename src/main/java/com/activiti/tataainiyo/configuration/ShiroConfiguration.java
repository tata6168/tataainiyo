package com.activiti.tataainiyo.configuration;

import com.activiti.tataainiyo.demo.User;
import com.activiti.tataainiyo.mapper.PermissionMapper;
import com.activiti.tataainiyo.mapper.UserMapper;
import com.activiti.tataainiyo.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class ShiroConfiguration {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher matcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //algorithm:算法
        //iterations:迭代次数/重复
        //Stored Credentials Hex Encoded：存储凭证十六进制编码
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(MD5Util.ENCRYPTIONNUM);
        //matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }
    @Bean("AuthorizingRealm")
    public AuthorizingRealm authorizingRealm(@Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher matcher){
        return new AuthorizingRealm() {
            @Override
            public String getName() {
                return "ainiyo";
            }
            @Override
            public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
                super.setCredentialsMatcher(matcher);
            }
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                User one = userMapper.selectOneById(user.getUserId());
                Set<String> snSet = new HashSet<>();
                one.getRoleList().forEach(r->{
                    r.getPermissionList().forEach(p->{
                        snSet.add(p.getSn());
                    });
                });
                info.addStringPermissions(snSet);
                return info;
            }
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
                User user = userMapper.authenticationName(usernamePasswordToken.getUsername());
                if(user==null){
                    return null;
                }
                ByteSource source = ByteSource.Util.bytes(MD5Util.SALT);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassWord(),source,"ainiyo");
                return info;
            }
        };
    }
    @Bean("DefaultWebSecurityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("AuthorizingRealm")AuthorizingRealm realm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("DefaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setUnauthorizedUrl("/unAuthorized.html");
        factoryBean.setLoginUrl("/login.html");
        factoryBean.setSuccessUrl("/test.html");
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("/**","anon");
//        stringStringHashMap.put("/component/task/task","anon");
//        stringStringHashMap.put("/success","anon");
//        stringStringHashMap.put("/login/**","anon");
//        stringStringHashMap.put("/**","anon");
//        stringStringHashMap.put("/templates/**","anon");
//        stringStringHashMap.put("/login.html","anon");
//        stringStringHashMap.put("/js/**","anon");
//        stringStringHashMap.put("/axios/**","anon");
//        stringStringHashMap.put("/elementuiCss/**","anon");
//        stringStringHashMap.put("/**","authc");
//        permissionMapper.selectAll().forEach(e->{
//            stringStringHashMap.put(e.getSn(),e.getPath());
//        });
        factoryBean.setFilterChainDefinitionMap(stringStringHashMap);

        return factoryBean;
    }
}
