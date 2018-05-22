package com.huitu.sjclub.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2018/3/27.
 * 用户表
 */
@Entity
@Table(name = "xx_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_user")
public class User extends BaseEntity<Long> {
   /* 用户名 userName
    密码 password
    性别 sex
    生日 birthDay
    身高:
    学校：
    班级:
    登录账号
            权限*/
    private String userLogo;
    private String userName;//用户名
    private String password;//密码
    private String sex;//性别
    private Date birthDate;//生日
    private String sg;//升高
    private String school;//学校
    private String className;//班级
    private String loginName;//登录账号
    private  Integer loginFailureCount;
    private Department department;//部门

    private Set<Role> roles = new HashSet<Role>();

    private Club club;

    private List<ClubActivity> clubActivityList=new ArrayList<ClubActivity>();
    //多
    private List<ClassTable> classTables=new ArrayList<ClassTable>();

    private List<PlanActivity> planActivities=new ArrayList<PlanActivity>();

    private List<SpotCheckIn> spotCheckIns=new ArrayList<SpotCheckIn>();

    private List<ClubNotice> clubNotices=new ArrayList<ClubNotice>();

    private List<ClubFile> clubFiles=new ArrayList<ClubFile>();

    private List<Meeting> meetings;

    private String  loginIp;
    private Date loginDate;

    private ClubActivity clubActivity;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<ClubFile> getClubFiles() {
        return clubFiles;
    }

    public void setClubFiles(List<ClubFile> clubFiles) {
        this.clubFiles = clubFiles;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<PlanActivity> getPlanActivities() {
        return planActivities;
    }

    public void setPlanActivities(List<PlanActivity> planActivities) {
        this.planActivities = planActivities;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public ClubActivity getClubActivity() {
        return clubActivity;
    }

    public void setClubActivity(ClubActivity clubActivity) {
        this.clubActivity = clubActivity;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<ClubNotice> getClubNotices() {
        return clubNotices;
    }

    public void setClubNotices(List<ClubNotice> clubNotices) {
        this.clubNotices = clubNotices;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<SpotCheckIn> getSpotCheckIns() {
        return spotCheckIns;
    }

    public void setSpotCheckIns(List<SpotCheckIn> spotCheckIns) {
        this.spotCheckIns = spotCheckIns;
    }


    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<ClassTable> getClassTables() {
        return classTables;
    }

    public void setClassTables(List<ClassTable> classTables) {
        this.classTables = classTables;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<ClubActivity> getClubActivityList() {
        return clubActivityList;
    }

    public void setClubActivityList(List<ClubActivity> clubActivityList) {
        this.clubActivityList = clubActivityList;
    }

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "xx_user_role")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
}
