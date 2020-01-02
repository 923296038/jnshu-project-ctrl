package com.jnshu.student.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:53
 **/

public class Student {
    @Min(value = 0,message = "openId要大于0")
    private String openId;
    @NotBlank(message = "姓名不为空")
    private String name;
    private String image;
    private int grade;
    private String area;
    private int beans_amount;
    @Email(message = "不符合邮箱格式")
    private String email;
    private Long phone;
    private int status;
    private int total_check;
    private int highest_continued_check;
    private int current_continued_check;
    private String token;
    private Long create_at;
    private Long update_at;
    private String create_by;
    private String update_by;

    @Override
    public String toString() {
        return "Student{" +
                "openId='" + openId + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", grade=" + grade +
                ", area='" + area + '\'' +
                ", beans_amount=" + beans_amount +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", status=" + status +
                ", total_check=" + total_check +
                ", highest_continued_check=" + highest_continued_check +
                ", current_continued_check=" + current_continued_check +
                ", token='" + token + '\'' +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                ", create_by='" + create_by + '\'' +
                ", update_by='" + update_by + '\'' +
                '}';
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getBeans_amount() {
        return beans_amount;
    }

    public void setBeans_amount(int beans_amount) {
        this.beans_amount = beans_amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_check() {
        return total_check;
    }

    public void setTotal_check(int total_check) {
        this.total_check = total_check;
    }

    public int getHighest_continued_check() {
        return highest_continued_check;
    }

    public void setHighest_continued_check(int highest_continued_check) {
        this.highest_continued_check = highest_continued_check;
    }

    public int getCurrent_continued_check() {
        return current_continued_check;
    }

    public void setCurrent_continued_check(int current_continued_check) {
        this.current_continued_check = current_continued_check;
    }

    public Student() {
    }
}
