package com.huitu.sjclub.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_club_file")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_club_file")
public class ClubFile extends BaseEntity<Long> {
    private String fileName;
    private String fileUrl;

    private User user;

    private Club club;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
