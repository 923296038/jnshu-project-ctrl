package com.jnshu.student.model;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:59
 **/
public class ArticleCollection{
    private Long sid;
    private Long aid;

    @Override
    public String toString() {
        return "ArticleCollection{" +
                "sid=" + sid +
                ", aid=" + aid +
                '}';
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public ArticleCollection() {
    }

    public ArticleCollection(Long sid, Long aid) {
        this.sid = sid;
        this.aid = aid;
    }
}
