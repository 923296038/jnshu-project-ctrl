package com.jnshu.article.model;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:53
 **/
public class Student {
    private Long id;
    private String name;
    private String image;
    private int grade;
    private String area_province;
    private String area_city;
    private int beans_amount;
    private String email;
    private Long phone;
    private int account_status;
    private int total_check;
    private int highest_continued_check;
    private int current_continued_check;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", grade=" + grade +
                ", area_province='" + area_province + '\'' +
                ", area_city='" + area_city + '\'' +
                ", beans_amount=" + beans_amount +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", account_status=" + account_status +
                ", total_check=" + total_check +
                ", highest_continued_check=" + highest_continued_check +
                ", current_continued_check=" + current_continued_check +
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

    public String getArea_province() {
        return area_province;
    }

    public void setArea_province(String area_province) {
        this.area_province = area_province;
    }

    public String getArea_city() {
        return area_city;
    }

    public void setArea_city(String area_city) {
        this.area_city = area_city;
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

    public int getAccount_status() {
        return account_status;
    }

    public void setAccount_status(int account_status) {
        this.account_status = account_status;
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
