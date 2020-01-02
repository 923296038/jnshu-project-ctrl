package com.jnshu.article.model;

import java.io.Serializable;

public class Article implements Serializable{
    private Long id;
    private String title;
    private String type;
    private String image;
    private String author;
    private Long number_of_likes;
    private String introduction;
    private Long number_of_collections;
    private String content;
    private Long status;
    private Long create_at;
    private Long update_at;
    private String create_by;
    private String update_by;
    private Long likes_min;
    private Long likes_max;
    private Long collections_min;
    private Long collections_max;
    private Long like_sid;
    private Long collection_sid;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    public Long getLike_sid() {
        return like_sid;
    }

    public void setLike_sid(Long like_sid) {
        this.like_sid = like_sid;
    }

    public Long getCollection_sid() {
        return collection_sid;
    }

    public void setCollection_sid(Long collection_sid) {
        this.collection_sid = collection_sid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getNumber_of_likes() {
        return number_of_likes;
    }

    public void setNumber_of_likes(Long number_of_likes) {
        this.number_of_likes = number_of_likes;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getNumber_of_collections() {
        return number_of_collections;
    }

    public void setNumber_of_collections(Long number_of_collections) {
        this.number_of_collections = number_of_collections;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public Long getLikes_min() {
        return likes_min;
    }

    public void setLikes_min(Long likes_min) {
        this.likes_min = likes_min;
    }

    public Long getLikes_max() {
        return likes_max;
    }

    public void setLikes_max(Long likes_max) {
        this.likes_max = likes_max;
    }

    public Long getCollections_min() {
        return collections_min;
    }

    public void setCollections_min(Long collections_min) {
        this.collections_min = collections_min;
    }

    public Long getCollections_max() {
        return collections_max;
    }

    public void setCollections_max(Long collections_max) {
        this.collections_max = collections_max;
    }

    public Article() {
    }
}
