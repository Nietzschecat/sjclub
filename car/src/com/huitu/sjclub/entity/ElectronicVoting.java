package com.huitu.sjclub.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_electronic_voting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_electronic_voting")
public class ElectronicVoting extends BaseEntity<Long> {
    /*投票选项
            投票截止时间
    投票选项票数
            投票者
    投票内容*/
    private List<ElectronicVotingItem> electronicVotingItemList=new ArrayList<ElectronicVotingItem>();
    private String content;
    private Date startTime;
    private Date endTime;

    @OneToMany(mappedBy = "electronicVoting",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<ElectronicVotingItem> getElectronicVotingItemList() {
        return electronicVotingItemList;
    }

    public void setElectronicVotingItemList(List<ElectronicVotingItem> electronicVotingItemList) {
        this.electronicVotingItemList = electronicVotingItemList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
