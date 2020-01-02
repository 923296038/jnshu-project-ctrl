package com.jnshu.student.model;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 10:00
 **/
public class VideoCollection{
    private Long vid;
    private Long sid;

    @Override
    public String toString() {
        return "VideoCollection{" +
                "vid=" + vid +
                ", sid=" + sid +
                '}';
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public VideoCollection() {
    }
}
