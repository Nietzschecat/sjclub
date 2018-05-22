package com.huitu.sjclub.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/4/6.
 */
@Entity
@Table(name = "xx_plan_activity")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_plan_activity")
public class PlanActivity extends BaseEntity<Long> {
    private ClubActivity clubActivity;

    private Department department;

    private String time;//时间

    private String content;//内容

    private String timeLen;//时长

    private String bz;//备注

    private User user;

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
    public ClubActivity getClubActivity() {
        return clubActivity;
    }

    public void setClubActivity(ClubActivity clubActivity) {
        this.clubActivity = clubActivity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeLen() {
        return timeLen;
    }

    public void setTimeLen(String timeLen) {
        this.timeLen = timeLen;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
