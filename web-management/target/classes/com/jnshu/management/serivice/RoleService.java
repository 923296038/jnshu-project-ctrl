package com.jnshu.management.serivice;

import com.jnshu.management.model.Module;
import com.jnshu.management.model.Role;

import java.util.ArrayList;
import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Boolean createRole(Role role);
    //改角色表,删除r-m中相关,新增r-m
    Boolean updateRole(Role role);
    //删角色,删a-r,删r-m
    Boolean deleteRole(Long rid);
    //根据角色id查模块
    ArrayList<Long> getModules(Long rid);
}
