package com.huitu.sjclub.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_class_table")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_class_table")
public class ClassTable extends BaseEntity<Long> {
    /*用户id
            无课时
    课长*/
    private User user;//1    user >>>calsstable
    private String noClass;//没有课的时间
    private String classTime;//课时
    private String address;//上课地点

    //这里和用户一对多
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNoClass() {
        return noClass;
    }

    public void setNoClass(String noClass) {
        this.noClass = noClass;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
