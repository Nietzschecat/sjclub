package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.dao.ElectronicVotingItemDao;
import com.huitu.sjclub.entity.ElectronicVotingItem;
import com.huitu.sjclub.service.ElectronicVotingItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("electronicVotingItemServiceImpl")
public class ElectronicVotingItemServiceImpl extends BaseServiceImpl<ElectronicVotingItem,Long> implements ElectronicVotingItemService {
    @Resource(name = "electronicVotingItemDaoImpl")
    private ElectronicVotingItemDao electronicVotingItemDao;
}
