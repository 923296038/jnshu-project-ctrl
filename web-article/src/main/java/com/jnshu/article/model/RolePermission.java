package com.jnshu.article.model;

import java.io.Serializable;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:59
 **/
public class RolePermission{
    private Long rid;
    private Long pid;

    @Override
    public String toString() {
        return "RolePermission{" +
                "rid=" + rid +
                ", pid=" + pid +
                '}';
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public RolePermission() {
    }
}
