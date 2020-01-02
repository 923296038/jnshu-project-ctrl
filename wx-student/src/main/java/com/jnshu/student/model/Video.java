package com.jnshu.student.model;

/**
 * @description:
 * @authoer:Wang
 * @create_at:2019-11-29 09:49
 **/
public class Video {
    private Long id;
    private String title;
    private int video_time;
    private String video_image;
    private String grade;
    private String subject;
    private String teacher;
    private String teacher_image;
    private int number_of_likes;
    private int number_of_collections;
    private String video_url;
    private String content;
    private String introduction;
    private int status;
    private Long collect_at;
    private Long create_at;
    private Long update_at;
    private String create_by;
    private String update_by;

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", video_time=" + video_time +
                ", video_image='" + video_image + '\'' +
                ", grade='" + grade + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", teacher_image='" + teacher_image + '\'' +
                ", number_of_likes=" + number_of_likes +
                ", number_of_collections=" + number_of_collections +
                ", video_url='" + video_url + '\'' +
                ", content='" + content + '\'' +
                ", introduction='" + introduction + '\'' +
                ", status=" + status +
                ", collect_at=" + collect_at +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVideo_time() {
        return video_time;
    }

    public void setVideo_time(int video_time) {
        this.video_time = video_time;
    }

    public String getVideo_image() {
        return video_image;
    }

    public void setVideo_image(String video_image) {
        this.video_image = video_image;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher_image() {
        return teacher_image;
    }

    public void setTeacher_image(String teacher_image) {
        this.teacher_image = teacher_image;
    }

    public int getNumber_of_likes() {
        return number_of_likes;
    }

    public void setNumber_of_likes(int number_of_likes) {
        this.number_of_likes = number_of_likes;
    }

    public int getNumber_of_collections() {
        return number_of_collections;
    }

    public void setNumber_of_collections(int number_of_collections) {
        this.number_of_collections = number_of_collections;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getCollect_at() {
        return collect_at;
    }

    public void setCollect_at(Long collect_at) {
        this.collect_at = collect_at;
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

    public Video() {
    }
}
