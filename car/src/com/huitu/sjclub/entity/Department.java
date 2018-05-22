package com.huitu.sjclub.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_department")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_department")
public class Department  extends BaseEntity<Long>{
    /*部门名称
            部门介绍
    部门成员
            部门*/
    private String name;//

    private String introduction;//简介

    private List<User> user=new ArrayList<User>();

    private Club club;
    private List<PlanActivity> planActivities=new ArrayList<PlanActivity>();

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<PlanActivity> getPlanActivities() {
        return planActivities;
    }

    public void setPlanActivities(List<PlanActivity> planActivities) {
        this.planActivities = planActivities;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @OneToMany(mappedBy = "department",cascade = CascadeType.REMOVE ,fetch = FetchType.LAZY)
    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

}
