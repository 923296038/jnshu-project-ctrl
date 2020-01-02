package com.jnshu.article.model;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:53
 **/
public class Teacher {
    private Long id;
    private String name;
    private String image;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
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

    public Teacher() {
    }

    public Teacher(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
