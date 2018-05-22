package com.huitu.sjclub.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_club")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_club")
public class Club extends BaseEntity<Long> {
   /* 社团名称
    社团简介
    社团成员*/
    private String name;
    private String logo;
    private String info;
    private String tenGood;//十大校佳

    private List<ClubActivity> clubActivities=new ArrayList<ClubActivity>();
    private List<User> users=new ArrayList<User>();

    private List<Meeting> meetings=new ArrayList<Meeting>();

    private List<ClubFile> clubFiles;

    private List<ClubNotice> clubNotices;

    private List<Department> departments=new ArrayList<Department>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    public List<ClubActivity> getClubActivities() {
        return clubActivities;
    }

    public void setClubActivities(List<ClubActivity> clubActivities) {
        this.clubActivities = clubActivities;
    }

    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTenGood() {
        return tenGood;
    }

    public void setTenGood(String tenGood) {
        this.tenGood = tenGood;
    }

    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    public List<ClubFile> getClubFiles() {
        return clubFiles;
    }

    public void setClubFiles(List<ClubFile> clubFiles) {
        this.clubFiles = clubFiles;
    }
    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    public List<ClubNotice> getClubNotices() {
        return clubNotices;
    }

    public void setClubNotices(List<ClubNotice> clubNotices) {
        this.clubNotices = clubNotices;
    }


    @OneToMany(mappedBy = "club",fetch = FetchType.LAZY)
    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
