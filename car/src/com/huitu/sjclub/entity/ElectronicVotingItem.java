package com.huitu.sjclub.entity;

import javax.persistence.*;

/**
 * Created by admin on 2018/3/27.
 */
@Entity
@Table(name = "xx_electronic_voting_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_electronic_voting_item")
public class ElectronicVotingItem extends BaseEntity<Long> {
    private Integer count;//投票
    private String itemName;//投票选项

    private ElectronicVoting electronicVoting;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false)
    public ElectronicVoting getElectronicVoting() {
        return electronicVoting;
    }

    public void setElectronicVoting(ElectronicVoting electronicVoting) {
        this.electronicVoting = electronicVoting;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
