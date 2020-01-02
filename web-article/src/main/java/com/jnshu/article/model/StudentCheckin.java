package com.jnshu.article.model;

import java.io.Serializable;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:52
 **/
public class StudentCheckin{
    private Long student_id;
    private int status;
    private Long date;

    @Override
    public String toString() {
        return "StudentCheckin{" +
                "student_id=" + student_id +
                ", status=" + status +
                ", date=" + date +
                '}';
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public StudentCheckin() {
    }

    public StudentCheckin(int status, Long date) {
        this.status = status;
        this.date = date;
    }
}
