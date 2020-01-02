package com.jnshu.management.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:53
 **/
public class Role implements Serializable {
    private Long id;
    private String role;
    private Long create_at;
    private Long update_at;
    private String create_by;
    private String update_by;
    private ArrayList<Long> modules;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                ", create_by='" + create_by + '\'' +
                ", update_by='" + update_by + '\'' +
                ", modules=" + modules +
                '}';
    }

    public ArrayList<Long> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Long> modules) {
        this.modules = modules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Long create_at) {
        this.create_at = create_at;
    }

    public Long getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Long update_at) {
        this.update_at = update_at;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Role() {
    }

    public Role(String role, Long create_at, Long update_at, String create_by, String update_by) {
        this.role = role;
        this.create_at = create_at;
        this.update_at = update_at;
        this.create_by = create_by;
        this.update_by = update_by;
    }
}
