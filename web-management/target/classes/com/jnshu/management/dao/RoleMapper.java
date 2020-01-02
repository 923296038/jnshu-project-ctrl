package com.jnshu.management.dao;



import com.jnshu.management.model.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-03 15:43
 **/
@Mapper
public interface RoleMapper {
    //角色列表 ×
    @Select("select role.id,role.role,role.create_at,role.create_by,role.update_at,role.update_by from role order by update_at desc")
    ArrayList<Role> getRoles();
    //角色查重
    @Select("select count(0) from role where role=#{role}")
    int roleExist(String role);
    //增角色
    @Insert("insert into role (role,create_at,update_at,create_by,update_by) values (#{role},#{create_at},#{update_at},#{create_by},#{update_by})")
    Boolean insertRole(Role role);
    //增角色-模块
    @Insert("insert into role_module (rid,mid) values ((select id from role where role=#{role}),#{mid})")
    Boolean insertRoleModule(String role, Long mid);
    //增角色-模块(以rid)
    @Insert("insert into role_module (rid,mid) values (#{rid},#{mid})")
    Boolean insertRoleModuleById(Long rid,Long mid);
    //改角色
    @Update("update role set role=#{role},update_at=#{update_at},update_by=#{update_by} where id=#{id}")
    Boolean updateRole(Role role);
    //改角色-模块
    Boolean updateRoleModule(Role role);
    //删角色
    @Delete("delete from role where id=#{rid}")
    int deleteRole(Long rid);
    //删账户-角色
    @Delete("delete from account_role where rid=#{rid}")
    int deleteAccountRole(Long rid);
    //删角色-模块
    @Delete("delete from role_module where rid = #{rid}")
    int deleteRoleModule(Long rid);
    //根据角色查模块
    @Select("select mid from role_module where rid=(select id from role where role=#{role})")
    ArrayList<Long> getRoleModules(String role);
    //根据id查模块
    @Select("SELECT module.id FROM role\n" +
            "LEFT JOIN role_module ON role.id = role_module.rid\n" +
            "LEFT JOIN module ON module.id = role_module.mid \n" +
            "WHERE 1 = 1 AND role.id = #{rid}")
    ArrayList<Long> getRoleModulesById(Long rid);
}
