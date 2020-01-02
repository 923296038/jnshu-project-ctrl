package com.jnshu.management.dao;


import com.jnshu.management.model.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-12-03 15:44
 **/
@Mapper
public interface ModuleMapper {
    //模块列表
    @Select("select * from module order by update_at desc")
    ArrayList<Module> getModules();

    //新增模块
    //编辑模块
    //删除模块
}
