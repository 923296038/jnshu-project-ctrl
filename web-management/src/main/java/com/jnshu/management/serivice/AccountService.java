package com.jnshu.management.serivice;


import com.alibaba.fastjson.JSONObject;
import com.jnshu.management.model.Account;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-11 10:12
 **/
public interface AccountService {
    Account findByName(String username);
    //编辑
    Boolean updateAccount(Account account);
    //查询
    ArrayList<Account> findAccount(Account account);
    //新增
    Boolean createAccount(Account account);
    //删除
    Boolean deleteAccount(Long id);
    //修改密码
    JSONObject updatePassword(Account account);
}
