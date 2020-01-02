package com.jnshu.management.model;

import java.io.Serializable;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:51
 **/
public class Module implements Serializable {
    private Long id;
    private String name;
    private String module_url;
    private Long parent_id;
    private Long create_at;
    private Long update_at;
    private String create_by;
    private String update_by;

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", module_url='" + module_url + '\'' +
                ", parent_id=" + parent_id +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                ", create_by='" + create_by + '\'' +
                ", update_by='" + update_by + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule_url() {
        return module_url;
    }

    public void setModule_url(String module_url) {
        this.module_url = module_url;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
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

    public Module(String name, String module_url, Long parent_id, Long create_at, Long update_at, String create_by, String update_by) {
        this.name = name;
        this.module_url = module_url;
        this.parent_id = parent_id;
        this.create_at = create_at;
        this.update_at = update_at;
        this.create_by = create_by;
        this.update_by = update_by;
    }

    public Module() {
    }
}
