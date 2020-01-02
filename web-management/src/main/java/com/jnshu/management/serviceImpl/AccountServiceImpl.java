package com.jnshu.management.serviceImpl;


import com.alibaba.fastjson.JSONObject;
import com.jnshu.management.dao.AccountMapper;
import com.jnshu.management.dao.RoleMapper;
import com.jnshu.management.model.Account;
import com.jnshu.management.serivice.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-11 10:12
 **/
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AccountServiceImpl implements AccountService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    RoleMapper roleMapper;
    private static final Logger log = LogManager.getLogger(AccountServiceImpl.class);

    @Override
    public Account findByName(String username) {
        return accountMapper.findByName(username);
    }

    @Override
    @Transactional
    public Boolean createAccount(Account account) {
        account.setCreate_at(System.currentTimeMillis());
        account.setUpdate_at(System.currentTimeMillis());
        log.error("createAccount 请求参数: "+account);
        if (accountMapper.findByName(account.getUsername())!=null){
            //用户名已重复则返回false
            log.error("createAccount,用户名已存在");
            return false;
        }
        if (roleMapper.roleExist(account.getRole())!=1){
            log.error("createAccount,角色不存在");
            return false;
        }
        if (!account.getPassword().equals(account.getPassword_repeat())){
            log.error("createAccount,两次密码不一致");
            return false;
        }
        //密码加密
        account.setPassword(new SimpleHash("MD5",account.getPassword(), ByteSource.Util.bytes(account.getUsername()),2).toHex());
        Boolean result1 = accountMapper.insertAccount(account);
        Boolean result2 = accountMapper.insertAccountRole(account.getUsername(),account.getRole());
        log.error("账户新增结果: "+result1+result2);
        if (!result1&result2){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("账户新增失败,事务回滚");
            return false;
        }
        return true;

    }
    @Override
    public JSONObject updatePassword(Account account) {
        account.setUpdate_at(System.currentTimeMillis());
        log.error("请求updatePassword: "+account);
        JSONObject object = new JSONObject();
        //密码加密
        String pre_password_encrypted = new SimpleHash("MD5",account.getPrevious_password(), ByteSource.Util.bytes(account.getUsername()),2).toHex();
        String new_password_encrypted = new SimpleHash("MD5",account.getPassword(), ByteSource.Util.bytes(account.getUsername()),2).toHex();
        if (!account.getPassword().equals(account.getPassword_repeat())){
            log.error("-------------两个新密码不相等,重新输入");
            object.put("code","0");
            object.put("message","修改密码失败.两次新密码不匹配");
            return object;
        }
        if (accountMapper.queryByNameAndPassword(account.getUsername(),pre_password_encrypted)!=1){
            log.error("---------------旧密码不正确");
            object.put("code","0");
            object.put("message","修改密码失败.旧密码不正确");
            return object;
        }
        if (accountMapper.findByName(account.getUsername()).getPassword().equals(new_password_encrypted)) {
            log.error("---------新密码不能与旧密码相等!");
            object.put("code","0");
            object.put("message","修改密码失败.新密码与旧密码相等");
            return object;
        }
        if (accountMapper.updatePassword(account.getUsername(),new_password_encrypted)){
            log.error("---------修改密码成功");
            object.put("code","1");
            object.put("message","修改密码成功");
        }
        return object;
    }

    @Override
    public Boolean updateAccount(Account account) {
        account.setUpdate_at(System.currentTimeMillis());
        log.error("updateAccount 请求参数: "+account);
        if (roleMapper.roleExist(account.getRole())!=1){
            log.error("角色不存在.");
            return false;
        }
        Boolean result1 = accountMapper.updateAccount(account);
        //删除原角色,增加新角色
        accountMapper.deleteAccountRole(account.getId());
        Boolean result2 = accountMapper.insertAccountRole(account.getUsername(),account.getRole());
        log.error("账户编辑结果: "+result1+result2);
        return result1&result2;
    }

    @Override
    public ArrayList<Account> findAccount(Account account) {
        //动态模糊
        log.error("findAccount 请求参数: "+account);
        ArrayList<Account> accounts = accountMapper.getAccount(account);
        log.error("搜索账户结果: "+accounts);
        return accounts;
    }
    @Override
    public Boolean deleteAccount(Long id) {
        log.error("请求删除账户 id:"+id);
        Boolean result1 = accountMapper.deleteAccount(id);
        Boolean result2 = accountMapper.deleteAccountRole(id);
        log.error("删除结果: "+result1+result2);
        return result1&result2;
    }


}
