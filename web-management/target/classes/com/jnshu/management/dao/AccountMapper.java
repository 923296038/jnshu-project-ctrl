package com.jnshu.management.dao;


import com.jnshu.management.model.Account;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface AccountMapper {
    //搜索账户+角色
    @Select("<script>select account.id,account.username,account.phone,account.create_at,account.update_at,account.create_by,account.update_by,role.role " +
            "from account left join account_role on account.id=account_role.aid\n" +
            "left join role on role.id=account_role.rid where 1=1\n" +
            "<if test=\"username!=null and username!=''\"> and account.username = #{username} </if>" +
            "<if test=\"role!=null and role!=''\"> and role.role=#{role} order by update_at desc</if></script> \n")
    ArrayList<Account> getAccount(Account account);
    //根据用户名搜模块
    @Select("select mid from role_module where rid=(select rid from account_role where aid=(select id from account where username=#{username}))")
    ArrayList<Long> getModules(String username);
    //根据用户名查账户
    @Select("select username,password from account where username=#{username}")
    Account findByName(String username);
    //根据用户名和密码查询
    @Select("select count(id) from account where username=#{username} and password=#{password}")
    int queryByNameAndPassword(String username,String password);
    //新增账户表
    @Insert("insert into account (username,phone,password,create_at,update_at,create_by,update_by) " +
            "values (#{username},#{phone},#{password},#{create_at},#{update_at},#{create_by},#{update_by})")
    Boolean insertAccount(Account account);
    //新增账户角色
    @Insert("insert into account_role (aid,rid) values ((select id from account where username=#{username}),(select id from role where role=#{role}))")
    Boolean insertAccountRole(String username, String role);
    //编辑账户表
    @Update("<script>update account<trim prefix=\"set\" suffixOverrides=\",\"> \n" +
            "<if test=\"username!=null and username != ''\">username=#{username},</if>\n" +
            "<if test=\"phone>=0\">phone=#{phone},</if> update_at=#{update_at} ,update_by=#{update_by} where id=#{id} </trim></script>")
    Boolean updateAccount(Account account);
    //删除账户表
    @Delete("delete from account where id = #{id}")
    Boolean deleteAccount(Long id);
    //删除账户角色
    @Delete("delete from account_role where aid=#{aid}")
    Boolean deleteAccountRole(Long aid);
    //修改密码
    @Update("update account set password=#{password} where username=#{username}")
    Boolean updatePassword(String username,String password);
}
