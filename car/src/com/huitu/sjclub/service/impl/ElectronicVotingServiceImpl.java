package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.dao.ElectronicVotingDao;
import com.huitu.sjclub.entity.ElectronicVoting;
import com.huitu.sjclub.entity.ElectronicVotingItem;
import com.huitu.sjclub.service.ElectronicVotingItemService;
import com.huitu.sjclub.service.ElectronicVotingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("electronicVotingServiceImpl")
public class ElectronicVotingServiceImpl extends BaseServiceImpl<ElectronicVoting,Long> implements ElectronicVotingService {
    @Resource(name = "electronicVotingDaoImpl")
    private ElectronicVotingDao electronicVotingDao;

    @Resource(name = "electronicVotingItemServiceImpl")
    private ElectronicVotingItemService electronicVotingItemService;
    @Override
    @Transactional
    public void saveElectronicVoting(ElectronicVoting electronicVoting,List<ElectronicVotingItem> list) {
        electronicVotingDao.persist(electronicVoting);
        for(ElectronicVotingItem electronicVotingItem:list){
            electronicVotingItem.setElectronicVoting(electronicVoting);
            electronicVotingItemService.save(electronicVotingItem);
        }
    }
}
