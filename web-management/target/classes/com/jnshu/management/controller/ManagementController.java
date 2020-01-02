package com.jnshu.management.controller;

import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jnshu.management.dao.AccountMapper;
import com.jnshu.management.dao.RoleMapper;
import com.jnshu.management.model.Account;
import com.jnshu.management.model.Role;
import com.jnshu.management.serivice.AccountService;
import com.jnshu.management.serivice.RoleService;
import com.netflix.client.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-04 15:58
 **/
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/management")
public class ManagementController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    AccountMapper accountMapper;
    Random random=new Random();
    private static final Logger log = LogManager.getLogger(ManagementController.class);

    /*
    登录
    */
    //登录.还需要测试rememberMe
    //username,password
    @PostMapping("/account/login")
    public JSONObject login(String username,String password){
        JSONObject object = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //2.获取当前用户对象
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //3.进行认证
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            log.error("对用户[" + username + "]进行登录验证");
            //保持登录
            currentUser.login(token);
            log.error("验证通过");
        }catch (UnknownAccountException uae){
            log.error("对用户[" + username + "]进行登录验证..验证未通过,未知的用户");
        }catch(IncorrectCredentialsException ice){
            log.error("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
        }catch(LockedAccountException lae){
            log.error("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            log.error("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.error("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            object.put("code","1");
            object.put("message","登录成功");
            log.info("用户[" + username + "]登录认证通过");
        }else{
            token.clear();
            object.put("code","0");
            object.put("message","登录失败");
            log.error("登录失败");
        }
        return object;
    }
    @RequestMapping("/account/logout")
    public JSONObject logout(){
        Subject subject = SecurityUtils.getSubject();
        JSONObject object = new JSONObject();
        if (subject != null) {
            subject.logout();
            object.put("code","1");
            object.put("message","退出登录");
            log.error("退出登录成功");
        }else {
            object.put("code","0");
            object.put("message","请求失败");
            log.error("退出登录请求失败");
        }
        return object;
    }
    //注册
    @PostMapping("/account/register")
    public String register(@RequestBody Account account){
        log.error("请求参数: "+account);
        String password = new SimpleHash("MD5",account.getPassword(),
                ByteSource.Util.bytes(account.getUsername()),2).toHex();
        log.error("加密密码: "+password);
        account.setPassword(password);
        return "注册结果: "+accountService.createAccount(account);
    }
    //修改密码
    @PutMapping("/account/password")
    public JSONObject changePassword(@RequestBody Account account){
        return accountService.updatePassword(account);
    }
    /*
    账户管理
    */
    //新增√
    //username,phone,password,password_repeat,role
    @PostMapping("/account")
    public JSONObject postAccount(@RequestBody Account account){
        JSONObject object = new JSONObject();
        if (accountService.createAccount(account)){
            object.put("code","1");
            object.put("message","账户新增成功");
        }else {
            object.put("code","0");
            object.put("message","账户新增失败");
        }
        return object;
    }
    //编辑√
    //id,username,phone,password,password_repeat,role
    @PutMapping("/account")
    public JSONObject putAccount(@RequestBody Account account){
        JSONObject object = new JSONObject();
        if (accountService.updateAccount(account)){
            object.put("code","1");
            object.put("message","账户编辑成功");
        }else {
            object.put("code","0");
            object.put("message","账户编辑失败");
        }
        return object;
    }
    //删除(删除账户-角色表中记录)√
    //id
    @DeleteMapping("/account")
    public JSONObject deleteAccount(long id){
        JSONObject object = new JSONObject();
        if (accountService.deleteAccount(id)){
            object.put("code","1");
            object.put("message","账户删除成功");
        }else{
            object.put("code","0");
            object.put("message","账户删除失败");
        }
        return object;
    }
    //查询.默认所有,可按角色和用户名查询√
    //*username,*role
    @GetMapping("/account")
    @Cacheable(value = "web-manage:",key = "#account.toString()+#page+#size")
    public JSONObject getAccount(Account account,
                                 @RequestParam(value = "page",defaultValue = "1") int page,
                                 @RequestParam(value = "size",defaultValue = "10") int size){
        JSONObject object = new JSONObject();
        PageHelper.startPage(page,size);
        log.error("查询请求: "+account);
        ArrayList<Account> accounts = accountService.findAccount(account);
        object.put("code","1");
        object.put("message","查询账户成功");
        object.put("data",new PageInfo<Account>(accounts));
        return object;
    }
    //用户名查模块
    @GetMapping("/account/modules")
    public JSONObject accountModules(String username){
        JSONObject object = new JSONObject();
        object.put("code","1");
        object.put("message","请求成功");
        object.put("data",accountMapper.getModules(username));
        return object;
    }
    /*
    角色管理
    */
    //角色列表√
    @GetMapping("/role")
    @Cacheable(value = "web-manage",key = "#page+#size")
    public JSONObject getRole(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "size",defaultValue = "10") int size){
        JSONObject object = new JSONObject();
        PageHelper.startPage(page,size);
        List<Role> roles = roleService.getRoles();
        object.put("code","1");
        object.put("message","请求成功");
        object.put("data",new PageInfo<Role>(roles));
        return object;
    }
    //获取模块列表
    @GetMapping("/role/modules")
    public JSONObject getModules(Long rid){
        JSONObject object = new JSONObject();
        object.put("code","1");
        object.put("message","获取列表成功");
        object.put("data",roleService.getModules(rid));
        return object;
    }
    //新增√
    //role,create_by,update_by,modules
    @PostMapping("/role")
    public JSONObject postRole(@RequestBody Role role){
        JSONObject object=new JSONObject();
        if (roleService.createRole(role)){
            object.put("code","1");
            object.put("message","新增角色成功");
        }else {
            object.put("code","0");
            object.put("message","新增角色失败");
        }
        return object;
    }
    //编辑√
    //id,role,update_by,modules
    @PutMapping("/role")
    public JSONObject putRole(@RequestBody Role role){
        JSONObject object=new JSONObject();
        if (roleService.updateRole(role)){
            object.put("code","1");
            object.put("message","编辑角色成功");
        }else {
            object.put("code","0");
            object.put("message","编辑角色失败");
        }
        return object;
    }
    //删除√
    //id
    @DeleteMapping("/role")
    public JSONObject deleteRole(Long id){
        JSONObject object=new JSONObject();
        if (roleService.deleteRole(id)){
            object.put("code","1");
            object.put("message","删除角色成功");
        }else {
            object.put("code","0");
            object.put("message","删除角色失败");
        }
        return object;
    }
}
