package com.jnshu.management.config;

import com.jnshu.management.realm.AccountRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shiyang
 * @PackageName com.ksy.config
 * @ClassName demo
 * @Description
 * @create 2019-11-07 20:09
 */
@Configuration
public class ShiroConfig {
    /**
     * 一个过滤器，拦截url
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须set项
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置登录成功跳转页面
        shiroFilterFactoryBean.setSuccessUrl("localhost:21026/success");
        //权限不足跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuthc");
        //拦截器，下面的属性就是配置不会被拦截的url
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问，封装在DefaultWebSecurityManager中
//        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/home","anon");
        filterChainDefinitionMap.put("/account","anon");
        filterChainDefinitionMap.put("/login/0","anon");
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/register/1","anon");
        filterChainDefinitionMap.put("/management/**","anon");
        filterChainDefinitionMap.put("/logout","logout");
        //设置权限拦截器，当权限为superManager才能访问后台
        filterChainDefinitionMap.put("/student/backDesk","authc,perms[/student/backDesk]");
        //这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    public SecurityManager securityManager() {
      DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
      //关联Realm
      defaultWebSecurityManager.setRealm(accountRealm());
       return defaultWebSecurityManager;
    }
    /**
     * 加入加密配置
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
    /**
     * 创建Realm以便调用
     * @return
     */
    @Bean
    public AccountRealm accountRealm() {
        AccountRealm accountRealm = new AccountRealm();
        // 告诉realm,使用credentialsMatcher加密算法类来验证密文
        accountRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        accountRealm.setCachingEnabled(false);
        return accountRealm;
    }
}
