package com.huitu.sjclub.service;

import com.huitu.sjclub.entity.ElectronicVoting;
import com.huitu.sjclub.entity.ElectronicVotingItem;

import java.util.List;

public interface ElectronicVotingService extends BaseService<ElectronicVoting,Long> {
    void saveElectronicVoting(ElectronicVoting electronicVoting,List<ElectronicVotingItem> itemList);

}
