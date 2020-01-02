package com.jnshu.management.realm;


import com.jnshu.management.model.Account;
import com.jnshu.management.serivice.AccountService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm，实现授权与认证
 */
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private AccountService accountService;

    /**
     * 用户授权
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
        PrincipalCollection principalCollection) {

        System.out.println("===执行授权===");
//
//        Subject subject = SecurityUtils.getSubject();
//        Account account = (Account) subject.getPrincipal();
//
//        if(account != null){
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            // 角色与权限字符串集合
//            Collection<String> rolesCollection = new HashSet<>();
//            Collection<String> permissionCollection = new HashSet<>();
//			// 读取并赋值用户角色与权限
//            Set<RoleBean> roles = user.getRole();
//            for(RoleBean role : roles){
//                rolesCollection.add(role.getName());
//                Set<PermissionBean> permissions = role.getPermissions();
//                for (PermissionBean permission : permissions){
//                    premissionCollection.add(permission.getUrl());
//                }
//                info.addStringPermissions(premissionCollection);
//            }
//            info.addRoles(rolesCollection);
//            return info;
//        }
        return null;
    }

    /**
     * 用户认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("开始调用doGetAuthInfo方法...");
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        //从数据库中查
        Account account = accountService.findByName(username);
        System.out.println("findByName: "+account);
        password = new SimpleHash("MD5",password,
                ByteSource.Util.bytes(username),2).toHex();
        System.out.println("加密后密码: "+password);
        if (account != null){
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            //参数1.用户认证的对象(subject.getPrincipal();返回的对象),
            //参数2.从数据库根据用户名查询到的用户的密码
            //参数3.把当前自定义的realm对象传给SimpleAuthenticationInfo,在配置文件需要注入
            System.out.println("return simpleAuthInfo...");
            return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(),ByteSource.Util.bytes(username),getName());
        }
        return null;
    }


}

