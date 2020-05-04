package com.ascending.training.club.model;

import com.ascending.training.club.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name=" images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",columnDefinition = "SERIAL")
    private Long id;

    @Column(name="file_name")
    private String fileName;

    @Column(name="s3Key")
    private String s3Key;

    @Column(name="creation_time")
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Image() {
    }

    public Image(User user,String fileName, String s3Key, LocalDateTime createTime) {
        this.fileName = fileName;
        this.s3Key = s3Key;
        this.createTime = createTime;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public User getUser() {return user; }

    public void setUser(User user) { this.user = user; }
}
