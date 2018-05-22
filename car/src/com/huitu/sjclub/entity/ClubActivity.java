package com.huitu.sjclub.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2018/3/27.
 * 社团活动
 */
@Entity
@Table(name = "xx_club_activity")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_club_activity")
public class ClubActivity extends BaseEntity<Long> {
    private String title;//活动标题
    private Date endTime;//结束时间
    private String content;//内容
    private User user;//创建者
    private String isStart;//是否开启

    private String images;//背景
    private List<User> activityUser=new ArrayList<User>();


    private Club club;

    private List<SpotCheckIn> spotCheckIns=new ArrayList<SpotCheckIn>();

    private List<PlanActivity> planActivities=new ArrayList<PlanActivity>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @OneToMany(mappedBy = "clubActivity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<PlanActivity> getPlanActivities() {
        return planActivities;
    }

    public void setPlanActivities(List<PlanActivity> planActivities) {
        this.planActivities = planActivities;
    }


    @OneToMany(mappedBy = "clubActivity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<User> getActivityUser() {
        return activityUser;
    }

    public void setActivityUser(List<User> activityUser) {
        this.activityUser = activityUser;
    }

    @OneToMany(mappedBy = "activity",fetch = FetchType.LAZY)
    public List<SpotCheckIn> getSpotCheckIns() {
        return spotCheckIns;
    }

    public void setSpotCheckIns(List<SpotCheckIn> spotCheckIns) {
        this.spotCheckIns = spotCheckIns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Lob
    @Column(length = 500000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
