package com.huitu.sjclub.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/3/27.
 *
 */
@Entity
@Table(name = "xx_club_notice")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_club_notice")
public class ClubNotice extends BaseEntity<Long> {
    private String title;//活动标题
    private String endTime;//结束时间
    private String startTime;//开始时间
    private String content;//内容
    private User user;//创建者
    private String isStart;//是否开启

    private Club club;//社团
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
