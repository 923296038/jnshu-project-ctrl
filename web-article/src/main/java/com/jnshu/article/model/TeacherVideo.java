package com.jnshu.article.model;

import java.io.Serializable;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 10:00
 **/
public class TeacherVideo{
    private Long tid;
    private Long vid;

    @Override
    public String toString() {
        return "TeacherVideo{" +
                "tid=" + tid +
                ", vid=" + vid +
                '}';
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public TeacherVideo() {
    }
}
