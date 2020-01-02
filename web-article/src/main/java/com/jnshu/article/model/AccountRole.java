package com.jnshu.article.model;

import java.io.Serializable;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:54
 **/
public class AccountRole{
    private Long mid;
    private Long rid;

    @Override
    public String toString() {
        return "AccountRole{" +
                "mid=" + mid +
                ", rid=" + rid +
                '}';
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public AccountRole() {
    }

    public AccountRole(Long mid, Long rid) {
        this.mid = mid;
        this.rid = rid;
    }
}
