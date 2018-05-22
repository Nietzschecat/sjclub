package com.huitu.sjclub.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_spotCheckIn")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_spotCheckIn")
public class SpotCheckIn extends BaseEntity<Long> {
    private User user;//签到者
    private String bz;//备注
    private Date sportCheckInDate;
    private ClubActivity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public ClubActivity getActivity() {
        return activity;
    }

    public void setActivity(ClubActivity activity) {
        this.activity = activity;
    }

    public Date getSportCheckInDate() {
        return sportCheckInDate;
    }

    public void setSportCheckInDate(Date sportCheckInDate) {
        this.sportCheckInDate = sportCheckInDate;
    }
}
