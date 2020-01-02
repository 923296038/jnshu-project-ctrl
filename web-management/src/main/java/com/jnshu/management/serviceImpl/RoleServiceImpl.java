package com.jnshu.management.serviceImpl;

import com.jnshu.management.dao.RoleMapper;
import com.jnshu.management.model.Module;
import com.jnshu.management.model.Role;
import com.jnshu.management.serivice.RoleService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-14 14:51
 **/
@Service
public class RoleServiceImpl implements RoleService {
    private final static Logger log = LogManager.getLogger(RoleServiceImpl.class);
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<Role> getRoles() {
        log.error("getting roles...");
        List<Role> roles = roleMapper.getRoles();
        log.error("getRoles: "+roles.size());
        return roles;
    }

    @Override
    @Transactional
    public Boolean createRole(Role role) {
        role.setCreate_at(System.currentTimeMillis());
        role.setUpdate_at(System.currentTimeMillis());
        log.error("creating role...:"+role);
        if (roleMapper.roleExist(role.getRole())==1){
            log.error("角色已重复");
            return false; }
        Boolean result1 = roleMapper.insertRole(role);
        //循环的方式增加role-module
        ArrayList<Long> modules = role.getModules();
        log.error("role.getModules: "+modules);
        for (Long mid:modules){
            roleMapper.insertRoleModule(role.getRole(),mid);
        }
        Boolean result2 = roleMapper.getRoleModules(role.getRole()).size()==modules.size();
        log.error("create role result: "+result1+result2);
        if (!result1&result2){
            log.error("角色新增失败.事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return result1&result2;
    }
    @Override
    @Transactional
    public Boolean updateRole(Role role) {
        role.setUpdate_at(System.currentTimeMillis());
        log.error("updating role..."+role);
        Boolean result1 = roleMapper.updateRole(role);
        int result2 = roleMapper.deleteRoleModule(role.getId());
        ArrayList<Long> modules = role.getModules();
        log.error("角色对应模块mid: "+modules);
        for (Long mid:modules){
            roleMapper.insertRoleModuleById(role.getId(),mid);
        }
        Boolean result3 = roleMapper.getRoleModulesById(role.getId()).size()==modules.size();
        if (!result3){
            log.error("角色编辑失败.事务回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.error("update role results: "+result1+result2+result3);
        return result1&result3 ;
    }

    @Override
    public Boolean deleteRole(Long rid) {
        log.error("deleting role..."+rid);
        int result1 = roleMapper.deleteRole(rid);
        int result2 = roleMapper.deleteAccountRole(rid);
        int result3 = roleMapper.deleteRoleModule(rid);
        log.error("delete role results: "+result1+result2+result3);
        return true ;
    }

    @Override
    public ArrayList<Long> getModules(Long rid) {
        log.error("service.getModules: "+rid);
        ArrayList<Long> modules = roleMapper.getRoleModulesById(rid);
        log.error("模块列表: "+modules);
        return modules;
    }
}
