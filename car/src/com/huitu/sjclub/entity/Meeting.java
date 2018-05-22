package com.huitu.sjclub.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_meeting" )
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_meeting")
public class Meeting extends BaseEntity<Long> {
    /*会议主题，
    会议摘要
            会议参与者
    会议内容*/
    private String title;
    private String info;
    private User user;
    private String content;

    private Club club;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
